package com.rafapps.taskerhealthconnect.aggregated

import android.content.Context
import android.util.Log
import com.joaomgcd.taskerpluginlibrary.action.TaskerPluginRunnerAction
import com.joaomgcd.taskerpluginlibrary.input.TaskerInput
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResult
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResultErrorWithOutput
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResultSucess
import com.rafapps.taskerhealthconnect.HealthConnectRepository
import com.rafapps.taskerhealthconnect.R
import kotlinx.coroutines.runBlocking
import java.time.Instant
import java.time.ZonedDateTime

class AggregatedHealthDataActionRunner :
    TaskerPluginRunnerAction<AggregatedHealthDataInput, AggregatedHealthDataOutput>() {

    private val TAG = "AggregatedHealthDataActionRunner"
    private val errCode = 1

    override val notificationProperties
        get() = NotificationProperties(iconResId = R.drawable.ic_launcher_foreground)

    override fun run(
        context: Context,
        input: TaskerInput<AggregatedHealthDataInput>
    ): TaskerPluginResult<AggregatedHealthDataOutput> {
        Log.d(TAG, "run: ${input.regular}")
        val repository = HealthConnectRepository(context)
        val days = runCatching { input.regular.days.toLong() }.getOrElse { e ->
            Log.e(TAG, "invalid input: ${input.regular.days}")
            return TaskerPluginResultErrorWithOutput(errCode, e.toString())
        }
        val offsetTime = daysToOffsetTime(days)

        if (!repository.isAvailable() || runBlocking { !repository.hasPermissions() }) {
            val errMessage = context.getString(R.string.health_connect_unavailable_or_permissions)
            Log.e(TAG, errMessage)
            return TaskerPluginResultErrorWithOutput(errCode, errMessage)
        }

        return try {
            val data = runBlocking {
                repository.getAggregateData(startTime = offsetTime)
            }
            TaskerPluginResultSucess(AggregatedHealthDataOutput(aggregatedHealthData = data.toString()))
        } catch (e: Exception) {
            Log.e(TAG, "run error", e)
            TaskerPluginResultErrorWithOutput(errCode, e.toString())
        }
    }

    companion object {
        // get the midnight instant of 'daysOffset' days ago
        fun daysToOffsetTime(daysOffset: Long): Instant {
            val zonedDateTime = ZonedDateTime.now()
            return zonedDateTime.minusDays(daysOffset)
                .minusHours(zonedDateTime.hour.toLong())
                .minusMinutes(zonedDateTime.minute.toLong())
                .minusSeconds(zonedDateTime.second.toLong())
                .toInstant()
        }
    }
}
