package com.rafapps.taskerhealthconnect

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.request.AggregateGroupByDurationRequest
import androidx.health.connect.client.time.TimeRangeFilter
import java.time.Duration
import java.time.Instant

class HealthConnectRepository(private val context: Context) {

    private val playStoreUri =
        "https://play.google.com/store/apps/details?id=com.google.android.apps.healthdata"

    private val client by lazy { HealthConnectClient.getOrCreate(context) }

    val permissions = setOf(
        HealthPermission.createReadPermission(StepsRecord::class)
    )

    fun installHealthConnect() {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(playStoreUri)
            setPackage("com.android.vending")
        }

        runCatching { context.startActivity(intent) }
    }

    fun isAvailable(): Boolean = HealthConnectClient.isAvailable(context)

    suspend fun hasPermissions(): Boolean {
        val granted = client.permissionController.getGrantedPermissions(permissions)
        return granted.containsAll(permissions)
    }

    suspend fun countSteps(
        startTime: Instant,
        endTime: Instant
    ): Long {
        val response = client.aggregateGroupByDuration(
            AggregateGroupByDurationRequest(
                metrics = setOf(StepsRecord.COUNT_TOTAL),
                timeRangeFilter = TimeRangeFilter.between(startTime, endTime),
                timeRangeSlicer = Duration.ofDays(1)
            )
        )

        var count = 0L
        for (record in response) {
            count += record.result[StepsRecord.COUNT_TOTAL] ?: 0L
        }

        return count
    }
}
