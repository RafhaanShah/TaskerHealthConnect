package com.rafapps.taskerhealthconnect

import android.content.Context
import android.content.Intent
import android.health.connect.datatypes.Metadata
import android.net.Uri
import android.util.Log
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.Record
import androidx.health.connect.client.request.AggregateGroupByDurationRequest
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import androidx.health.connect.client.units.BloodGlucose
import androidx.health.connect.client.units.Energy
import androidx.health.connect.client.units.Length
import androidx.health.connect.client.units.Mass
import androidx.health.connect.client.units.Percentage
import androidx.health.connect.client.units.Power
import androidx.health.connect.client.units.Pressure
import androidx.health.connect.client.units.Temperature
import androidx.health.connect.client.units.Velocity
import androidx.health.connect.client.units.Volume
import org.json.JSONArray
import org.json.JSONObject
import java.time.Duration
import java.time.Instant
import java.time.ZoneOffset
import kotlin.reflect.KClass
import kotlin.reflect.KVisibility
import kotlin.reflect.full.memberProperties

class HealthConnectRepository(private val context: Context) {

    private val TAG = "HealthConnectRepository"

    private val playStoreUri =
        "https://play.google.com/store/apps/details?id=com.google.android.apps.healthdata"

    private val client by lazy { HealthConnectClient.getOrCreate(context) }

    val permissions by lazy {
        (singleRecordTypes + aggregateRecordTypes)
            .map { HealthPermission.getReadPermission(it) }.toSet()
    }

    fun installHealthConnect() {
        Log.d(TAG, "installHealthConnect")
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(playStoreUri)
            setPackage("com.android.vending")
        }

        runCatching { context.startActivity(intent) }
    }

    fun isAvailable(): Boolean =
        HealthConnectClient.getSdkStatus(context) == HealthConnectClient.SDK_AVAILABLE

    suspend fun hasPermissions(): Boolean {
        val granted = client.permissionController.getGrantedPermissions()
        return granted.containsAll(permissions)
    }

    suspend fun getAggregateData(
        startTime: Instant
    ): JSONArray {
        Log.d(TAG, "getAggregateData: $startTime")
        var dataPointSize = 0
        val result = JSONArray()
        val response = client.aggregateGroupByDuration(
            AggregateGroupByDurationRequest(
                metrics = aggregateMetricTypes.values.toSet(),
                timeRangeFilter = TimeRangeFilter.after(startTime),
                timeRangeSlicer = Duration.ofDays(1)
            )
        )

        // loop through the "buckets" of data where each bucket is 1 day
        response.forEach { aggregationResult ->
            val data = mutableMapOf<String, Any>()
            data["startTime"] = aggregationResult.startTime
            data["endTime"] = aggregationResult.endTime

            // check for each data type in each bucket
            aggregateMetricTypes.forEach { metricType ->
                aggregationResult.result[metricType.value]?.let { dataPoint ->
                     extractDataPointValue(dataPoint)?.let {
                         dataPointSize++
                         data[metricType.key] = it
                    }
                }
            }

            // put the data for that day as an object in the output array
            result.put(JSONObject(data.toMap()))
        }

        Log.d(TAG, "getAggregateData result size: ${response.size}," +
                " dataPointSize: $dataPointSize")
        return result
    }


    @Suppress("UNCHECKED_CAST")
    suspend fun getData(recordClass: String, startTime: Instant): JSONArray {
        Log.d(TAG, "getData: $recordClass, $startTime")
        val result = JSONArray()
        // convert it to a specific record type via reflection, will throw if fails
        val kClass = Class.forName("androidx.health.connect.client.records.$recordClass")
            .kotlin as KClass<Record>

        // fetch the records for the given type
        val response = client.readRecords(
            ReadRecordsRequest(
                recordType = kClass,
                timeRangeFilter = TimeRangeFilter.after(startTime)
            )
        )

        // process each record in the response
        for (record in response.records) {
            result.put(JSONObject(extractDataRecordValue(record)))
        }

        Log.d(TAG, "getData result size: ${response.records.size}")
        return result
    }

    // use the actual data type to get the correct value
    private fun extractDataPointValue(dataPoint: Comparable<*>): Any? {
        return when (dataPoint) {
            is Boolean -> dataPoint
            is BloodGlucose -> dataPoint.inMilligramsPerDeciliter
            is Double -> dataPoint
            is Duration -> dataPoint.toMinutes()
            is Energy -> dataPoint.inCalories
            is Length -> dataPoint.inMeters
            is Instant -> dataPoint.toString()
            is Int -> dataPoint
            is Long -> dataPoint
            is Mass -> dataPoint.inKilograms
            is Percentage -> dataPoint.value
            is Power -> dataPoint.inWatts
            is Pressure -> dataPoint.inMillimetersOfMercury
            is String -> dataPoint
            is Temperature -> dataPoint.inCelsius
            is Velocity -> dataPoint.inMetersPerSecond
            is Volume -> dataPoint.inLiters
            else -> null
        }
    }

    // cast to each record type to process individually
    @Suppress("UNCHECKED_CAST")
    private fun extractDataRecordValue(record: Record): Map<String, Any> {
        val result = mutableMapOf<String, Any>()
        val properties = (record::class as KClass<Record>)
            .memberProperties.filter { it.visibility == KVisibility.PUBLIC }

        for (prop in properties) {
            val value = prop.get(record) ?: continue
            val key = prop.name

            when(value) {
                is Comparable<*> ->  extractDataPointValue(value)?.let { result[key] = it }
                is List<*> -> result[key] = extractListData(value)
            }
        }
        return result
    }

    // extract records where there is a list of samples
    @Suppress("UNCHECKED_CAST")
    private fun extractListData(samples: List<*>): List<Any> {
        val result = mutableListOf<Any>()

        for (sample in samples) {
            if (sample == null) continue
            val obj = mutableMapOf<String, Any>()
            val properties = (sample::class as KClass<Any>)
                .memberProperties.filter { it.visibility == KVisibility.PUBLIC }

            for (prop in properties) {
                val value = prop.get(sample) ?: continue
                val key = prop.name
                if (value is Comparable<*>) {
                    extractDataPointValue(value)?.let { obj[key] = it }
                }
            }
            result.add(obj)
        }
        return result
    }
}
