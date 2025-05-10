package com.rafapps.taskerhealthconnect.read

import android.content.Context
import android.util.Log
import androidx.health.connect.client.records.Record
import com.joaomgcd.taskerpluginlibrary.action.TaskerPluginRunnerAction
import com.joaomgcd.taskerpluginlibrary.input.TaskerInput
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResult
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResultErrorWithOutput
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResultSucess
import com.rafapps.taskerhealthconnect.HealthConnectRepository
import com.rafapps.taskerhealthconnect.HealthConnectRepositoryProvider
import com.rafapps.taskerhealthconnect.R
import com.rafapps.taskerhealthconnect.Serializer
import com.rafapps.taskerhealthconnect.healthConnectRecordsPackage
import kotlinx.coroutines.runBlocking
import java.time.Instant

class ReadDataActionRunner(
    private val repositoryProvider: HealthConnectRepositoryProvider = { HealthConnectRepository(it) },
    private val serializer: Serializer = Serializer()
) :
    TaskerPluginRunnerAction<ReadDataInput, ReadDataOutput>() {

    private val TAG = "ReadDataActionRunner"
    private val errCode = 1

    override val notificationProperties
        get() = NotificationProperties(iconResId = R.drawable.ic_launcher_foreground)

    @Suppress("UNCHECKED_CAST")
    override fun run(
        context: Context,
        input: TaskerInput<ReadDataInput>
    ): TaskerPluginResult<ReadDataOutput> {
        Log.d(TAG, "run: ${input.regular}")
        val repository = repositoryProvider(context)
        val startTime =
            runCatching { Instant.ofEpochMilli(input.regular.startTime.toLong()) }.getOrElse { e ->
                Log.e(TAG, "invalid input: ${input.regular.startTime}")
                return TaskerPluginResultErrorWithOutput(errCode, e.toString())
            }

        val endTime =
            runCatching { Instant.ofEpochMilli(input.regular.endTime.toLong()) }.getOrElse { e ->
                Log.e(TAG, "invalid input: ${input.regular.endTime}")
                return TaskerPluginResultErrorWithOutput(errCode, e.toString())
            }

        return try {
            val clazz =
                Class.forName("$healthConnectRecordsPackage.${input.regular.recordType}")
                        as Class<Record>
            val data = runBlocking { repository.readData(clazz , startTime, endTime) }
            val serializedResult = serializer.toString(data)
            TaskerPluginResultSucess(ReadDataOutput(healthConnectResult = serializedResult))
        } catch (e: Exception) {
            Log.e(TAG, "run error", e)
            TaskerPluginResultErrorWithOutput(errCode, e.toString())
        }
    }
}
