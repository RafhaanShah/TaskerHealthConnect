package com.rafapps.taskerhealthconnect.delete

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
import com.rafapps.taskerhealthconnect.healthConnectRecordsPackage
import kotlinx.coroutines.runBlocking
import java.time.Instant

class DeleteDataActionRunner(
    private val repositoryProvider: HealthConnectRepositoryProvider = { HealthConnectRepository(it) }
) : TaskerPluginRunnerAction<DeleteDataInput, DeleteDataOutput>() {

    private val tag = "DeleteDataActionRunner"
    private val errCode = 1

    override val notificationProperties
        get() = NotificationProperties(iconResId = R.drawable.ic_launcher_foreground)

    @Suppress("UNCHECKED_CAST")
    override fun run(
        context: Context,
        input: TaskerInput<DeleteDataInput>
    ): TaskerPluginResult<DeleteDataOutput> = runCatching {
        Log.d(tag, "runner start: ${input.regular}")
        val repository = repositoryProvider(context)
        val startTime = Instant.ofEpochMilli(input.regular.startTime.toLong())
        val endTime = Instant.ofEpochMilli(input.regular.endTime.toLong())
        val clazz =
            Class.forName("$healthConnectRecordsPackage.${input.regular.recordType}")
                    as Class<Record>
        runBlocking { repository.deleteData(clazz, startTime, endTime) }
        Log.d(tag, "runner complete")
        TaskerPluginResultSucess(DeleteDataOutput(healthConnectResult = "{}"))
    }.getOrElse { e ->
        Log.e(tag, "run error", e)
        TaskerPluginResultErrorWithOutput(errCode, e.toString())
    }
}
