package com.rafapps.taskerhealthconnect

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.net.toUri
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.HealthConnectFeatures
import androidx.health.connect.client.HealthConnectFeatures.Companion.Feature
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
        val basePermissions = recordTypes
            .map { HealthPermission.getReadPermission(it) }
            .toMutableSet()

        if (isFeatureAvailable(HealthConnectFeatures.FEATURE_READ_HEALTH_DATA_HISTORY))
            basePermissions += HealthPermission.PERMISSION_READ_HEALTH_DATA_HISTORY

        if (isFeatureAvailable(HealthConnectFeatures.FEATURE_READ_HEALTH_DATA_IN_BACKGROUND))
            basePermissions += HealthPermission.PERMISSION_READ_HEALTH_DATA_IN_BACKGROUND

        basePermissions.toSet()
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

    fun openHealthConnect() {
        Log.d(tag, "openHealthConnect")
        val action =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
                "android.health.connect.action.HEALTH_HOME_SETTINGS"
            else "androidx.health.ACTION_HEALTH_CONNECT_SETTINGS"
        runCatching {
            context.startActivity(
                Intent(action)
            )
        }.onFailure {
            Log.d(tag, "openHealthConnect failed", it)
            Toast.makeText(context, R.string.failed_to_install, Toast.LENGTH_SHORT).show()
        }
    }

    fun openHealthConnectToolbox() {
        Log.d(tag, "openHealthConnectToolbox")
        val url =
            "https://developer.android.com/health-and-fitness/guides/health-connect/test/health-connect-toolbox"
        runCatching {
            val intent =
                context.packageManager.getLaunchIntentForPackage("androidx.health.connect.client.devtool")
                    ?: Intent(Intent.ACTION_VIEW, url.toUri())
            context.startActivity(intent)
        }.onFailure {
            Log.d(tag, "openHealthConnectToolbox failed", it)
            Toast.makeText(context, R.string.failed_to_open_toolbox, Toast.LENGTH_SHORT).show()
        }
    }

    fun isAvailable(): Boolean =
        HealthConnectClient.getSdkStatus(context) == HealthConnectClient.SDK_AVAILABLE

    suspend fun hasPermissions(permissions: Set<String>): Boolean {
        val granted = client.permissionController.getGrantedPermissions()
        val missing = permissions.filterNot { it in granted }
        Log.i(tag, "hasPermissions missing: $missing")
        return granted.containsAll(permissions)
    }

    suspend fun readAggregatedData(
        aggregateMetric: AggregateMetric<*>,
        startTime: Instant,
        endTime: Instant,
    ): AggregationResult {
        Log.d(tag, "readAggregatedData: ${aggregateMetric.javaClass.name} $startTime $endTime")
        val response = client.aggregate(
            AggregateRequest(
                metrics = setOf(aggregateMetric),
                timeRangeFilter = TimeRangeFilter.between(startTime, endTime)
            )
        )

        Log.d(
            tag,
            "readAggregatedData: result size ${response.longValues.size} + ${response.doubleValues.size}"
        )
        return response
    }

    suspend fun readData(
        recordClass: Class<Record>,
        startTime: Instant,
        endTime: Instant
    ): ReadRecordsResponse<Record> {
        Log.d(tag, "readData: $recordClass, $startTime, $endTime")
        val response = client.readRecords(
            ReadRecordsRequest(
                recordType = recordClass.kotlin,
                timeRangeFilter = TimeRangeFilter.between(startTime, endTime)
            )
        )

        Log.d(tag, "readData: result size ${response.records.size}")
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

    private fun isFeatureAvailable(@Feature feature: Int) =
        client.features.getFeatureStatus(feature) == HealthConnectFeatures.FEATURE_STATUS_AVAILABLE
}

typealias HealthConnectRepositoryProvider = (Context) -> HealthConnectRepository
