package com.rafapps.taskerhealthconnect

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.request.AggregateGroupByDurationRequest
import androidx.health.connect.client.time.TimeRangeFilter
import androidx.health.connect.client.units.Energy
import androidx.health.connect.client.units.Length
import androidx.health.connect.client.units.Mass
import androidx.health.connect.client.units.Power
import androidx.health.connect.client.units.Volume
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception
import java.time.Duration
import java.time.Instant

class HealthConnectRepository(private val context: Context) {

    private val TAG = "HealthConnectRepository"

    private val playStoreUri =
        "https://play.google.com/store/apps/details?id=com.google.android.apps.healthdata"

    private val client by lazy { HealthConnectClient.getOrCreate(context) }

    val permissions by lazy {
        aggregateRecordTypes.map { HealthPermission.getReadPermission(it) }.toSet()
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
        startTime: Instant, endTime: Instant
    ): JSONArray {
        Log.d(TAG, "getData: $startTime -> $endTime")
        var dataPointSize = 0
        val result = JSONArray()
        val response = client.aggregateGroupByDuration(
            AggregateGroupByDurationRequest(
                metrics = aggregateMetricTypes.values.toSet(),
                timeRangeFilter = TimeRangeFilter.between(startTime, endTime),
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
                    dataPointSize++
                    when (dataPoint) {
                        is Double -> data[metricType.key] = dataPoint
                        is Duration -> data[metricType.key] = dataPoint.toMinutes()
                        is Energy -> data[metricType.key] = dataPoint.inCalories
                        is Length -> data[metricType.key] = dataPoint.inMeters
                        is Long -> data[metricType.key] = dataPoint
                        is Mass -> data[metricType.key] = dataPoint.inKilograms
                        is Power -> data[metricType.key] = dataPoint.inWatts
                        is Volume -> data[metricType.key] = dataPoint.inLiters
                        else -> {
                            Log.e(TAG, "Unexpected data type: $dataPoint")
                            data[metricType.key] = dataPoint.toString()
                        }
                    }
                }
            }

            // put the data for that day as an object in the output array
            result.put(JSONObject(data.toMap()))
        }

        Log.d(TAG, "aggregationResults: ${response.size}, dataPointSize: $dataPointSize")
        return result
    }
}
