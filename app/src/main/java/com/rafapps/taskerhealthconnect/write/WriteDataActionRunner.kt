package com.rafapps.taskerhealthconnect.write

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

class WriteDataActionRunner :
    TaskerPluginRunnerAction<WriteDataInput, WriteDataOutput>() {

    private val TAG = "HealthDataActionRunner"
    private val errCode = 1

    override val notificationProperties
        get() = NotificationProperties(iconResId = R.drawable.ic_launcher_foreground)

    override fun run(
        context: Context,
        input: TaskerInput<WriteDataInput>
    ): TaskerPluginResult<WriteDataOutput> {
        Log.d(TAG, "run")
        val repository = HealthConnectRepository(context)

        if (!repository.isAvailable() || runBlocking { !repository.hasPermissions() }) {
            val errMessage = context.getString(R.string.health_connect_unavailable_or_permissions)
            Log.e(TAG, errMessage)
            return TaskerPluginResultErrorWithOutput(errCode, errMessage)
        }

        return try {
            val result = runBlocking {
                repository.writeData(
                    input.regular.recordType,
                    input.regular.recordInput
                )
            }
            TaskerPluginResultSucess(WriteDataOutput(healthConnectResult = result.toString()))
        } catch (e: Exception) {
            Log.e(TAG, "run error", e)
            TaskerPluginResultErrorWithOutput(errCode, e.toString())
        }
    }
}
