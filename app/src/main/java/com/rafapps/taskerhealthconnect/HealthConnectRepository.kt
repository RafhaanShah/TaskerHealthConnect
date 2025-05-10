package com.rafapps.taskerhealthconnect

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.net.toUri
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.aggregate.AggregateMetric
import androidx.health.connect.client.aggregate.AggregationResult
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.Record
import androidx.health.connect.client.request.AggregateRequest
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.response.InsertRecordsResponse
import androidx.health.connect.client.response.ReadRecordsResponse
import androidx.health.connect.client.time.TimeRangeFilter
import java.time.Instant

class HealthConnectRepository(
    private val context: Context,
    private val client: HealthConnectClient = HealthConnectClient.getOrCreate(context)
) {

    private val tag = "HealthConnectRepository"
    private val providerPackageName = "com.google.android.apps.healthdata"

    val readPermissions by lazy {
        recordTypes.map { HealthPermission.getReadPermission(it) }.toSet()
    }

    val writePermissions by lazy {
        recordTypes.map { HealthPermission.getWritePermission(it) }.toSet()
    }

    fun installHealthConnect() {
        Log.d(tag, "installHealthConnect")
        val uriString =
            "market://details?id=$providerPackageName&url=healthconnect%3A%2F%2Fonboarding"
        runCatching {
            context.startActivity(
                Intent(Intent.ACTION_VIEW).apply {
                    setPackage("com.android.vending")
                    data = uriString.toUri()
                    putExtra("overlay", true)
                }
            )
        }.onFailure {
            Log.d(tag, "installHealthConnect failed", it)
            Toast.makeText(context, R.string.failed_to_install, Toast.LENGTH_SHORT).show()
        }
    }

    fun isAvailable(): Boolean =
        HealthConnectClient.getSdkStatus(context) == HealthConnectClient.SDK_AVAILABLE

    suspend fun hasPermissions(permissions: Collection<String>): Boolean {
        val granted = client.permissionController.getGrantedPermissions()
        return granted.containsAll(permissions)
    }

    suspend fun readAggregatedData(
        aggregateMetric: AggregateMetric<*>,
        startTime: Instant,
        endTime: Instant,
    ): AggregationResult {
        Log.d(tag, "getAggregateData: ${aggregateMetric.javaClass.name} $startTime $endTime")
        val response = client.aggregate(
            AggregateRequest(
                metrics = setOf(aggregateMetric),
                timeRangeFilter = TimeRangeFilter.between(startTime, endTime)
            )
        )

        Log.d(
            tag,
            "getAggregateData: result size ${response.longValues.size} + ${response.doubleValues.size}"
        )
        return response
    }

    suspend fun readData(
        recordClass: Class<Record>,
        startTime: Instant,
        endTime: Instant
    ): ReadRecordsResponse<Record> {
        Log.d(tag, "getData: $recordClass, $startTime, $endTime")
        val response = client.readRecords(
            ReadRecordsRequest(
                recordType = recordClass.kotlin,
                timeRangeFilter = TimeRangeFilter.between(startTime, endTime)
            )
        )

        Log.d(tag, "getData: result size ${response.records.size}")
        return response
    }

    suspend fun writeData(
        records: List<Record>
    ): InsertRecordsResponse {
        Log.d(tag, "insertData: records size ${records.size}")
        val result = client.insertRecords(records)
        Log.d(tag, "insertData: result size ${result.recordIdsList.size}")
        return result
    }
}

typealias HealthConnectRepositoryProvider = (Context) -> HealthConnectRepository
