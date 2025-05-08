package com.rafapps.taskerhealthconnect

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.net.toUri
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.aggregate.AggregateMetric
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.Record
import androidx.health.connect.client.request.AggregateRequest
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import java.time.Instant
import kotlin.reflect.KClass

class HealthConnectRepository(
    private val context: Context,
    private val client: HealthConnectClient = HealthConnectClient.getOrCreate(context),
    private val serializer: Serializer = Serializer()
) {

    private val TAG = "HealthConnectRepository"

    private val playStoreUri =
        "https://play.google.com/store/apps/details?id=com.google.android.apps.healthdata"

    val permissions by lazy {
        (singleRecordTypes + aggregateRecordTypes)
            .flatMap {
                listOf(
                    HealthPermission.getReadPermission(it),
                    HealthPermission.getWritePermission(it)
                )
            }
            .toSet()
    }

    fun installHealthConnect() {
        Log.d(TAG, "installHealthConnect")
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = playStoreUri.toUri()
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

    suspend fun readAggregatedData(
        aggregateMetric: String,
        startTime: Instant,
        endTime: Instant,
    ): String {
        Log.d(TAG, "getAggregateData: $aggregateMetric $startTime $endTime")
        val split = aggregateMetric.split(".")
        val clazz = Class.forName("androidx.health.connect.client.aggregate.${split[0]}")
        val field = clazz.getField(split[1])
        val metric = field.get(null) as AggregateMetric<*>

        val response = client.aggregate(
            AggregateRequest(
                metrics = setOf(metric),
                timeRangeFilter = TimeRangeFilter.between(startTime, endTime)
            )
        )
        return serializer.toString(response)
    }

    @Suppress("UNCHECKED_CAST")
    suspend fun readData(recordClass: String, startTime: Instant, endTime: Instant): String {
        Log.d(TAG, "getData: $recordClass, $startTime")
        // convert it to a specific record type via reflection, will throw if fails
        val kClass = Class.forName("androidx.health.connect.client.records.$recordClass")
            .kotlin as KClass<Record>

        // fetch the records for the given type
        val response = client.readRecords(
            ReadRecordsRequest(
                recordType = kClass,
                timeRangeFilter = TimeRangeFilter.between(startTime, endTime)
            )
        )

        return serializer.toString(response)
    }

    @Suppress("UNCHECKED_CAST")
    suspend fun writeData(
        recordClass: String, recordString: String
    ): List<String> {
        Log.d(TAG, "insertData: $recordClass")
        val kClass = Class.forName("androidx.health.connect.client.records.$recordClass")
        val records: List<Any> = serializer.toObjectList(recordString, kClass, listOf("metadata"))
        val result = client.insertRecords(records as List<Record>)
        Log.d(TAG, "insertData: result size ${result.recordIdsList.size}")
        return result.recordIdsList
    }
}
