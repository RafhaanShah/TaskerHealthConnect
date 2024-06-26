package com.rafapps.taskerhealthconnect.healthdata

import android.content.Context
import android.util.Log
import com.joaomgcd.taskerpluginlibrary.action.TaskerPluginRunnerAction
import com.joaomgcd.taskerpluginlibrary.input.TaskerInput
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResult
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResultErrorWithOutput
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResultSucess
import com.rafapps.taskerhealthconnect.HealthConnectRepository
import com.rafapps.taskerhealthconnect.R
import com.rafapps.taskerhealthconnect.aggregated.AggregatedHealthDataActionRunner.Companion.daysToOffsetTime
import kotlinx.coroutines.runBlocking
import java.time.Instant
import java.time.ZonedDateTime

class HealthDataActionRunner :
    TaskerPluginRunnerAction<HealthDataInput, HealthDataOutput>() {

    private val TAG = "HealthDataActionRunner"
    private val errCode = 1

    override val notificationProperties
        get() = NotificationProperties(iconResId = R.drawable.ic_launcher_foreground)

    override fun run(
        context: Context,
        input: TaskerInput<HealthDataInput>
    ): TaskerPluginResult<HealthDataOutput> {
        Log.d(TAG, "run: ${input.regular}")
        val repository = HealthConnectRepository(context)
        val timeMs = runCatching { input.regular.fromTimeMillis.toLong() }.getOrElse { e ->
            Log.e(TAG, "invalid input: ${input.regular.fromTimeMillis}")
            return TaskerPluginResultErrorWithOutput(errCode, e.toString())
        }
        val offsetTime = Instant.ofEpochMilli(timeMs)

        if (!repository.isAvailable() || runBlocking { !repository.hasPermissions() }) {
            val errMessage = context.getString(R.string.health_connect_unavailable_or_permissions)
            Log.e(TAG, errMessage)
            return TaskerPluginResultErrorWithOutput(errCode, errMessage)
        }

        return try {
            val data = runBlocking {
                repository.getData(
                    input.regular.recordType,
                    startTime = offsetTime
                )
            }
            TaskerPluginResultSucess(HealthDataOutput(healthData = data.toString()))
        } catch (e: Exception) {
            Log.e(TAG, "run error", e)
            TaskerPluginResultErrorWithOutput(errCode, e.toString())
        }
    }
}
