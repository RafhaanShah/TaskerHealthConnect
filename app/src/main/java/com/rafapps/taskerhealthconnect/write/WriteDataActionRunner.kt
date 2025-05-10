package com.rafapps.taskerhealthconnect.write

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

class WriteDataActionRunner(
    private val repositoryProvider: HealthConnectRepositoryProvider = { HealthConnectRepository(it) },
    private val serializer: Serializer = Serializer()
) :
    TaskerPluginRunnerAction<WriteDataInput, WriteDataOutput>() {

    private val tag = "WriteDataActionRunner"
    private val errCode = 1

    override val notificationProperties
        get() = NotificationProperties(iconResId = R.drawable.ic_launcher_foreground)

    @Suppress("UNCHECKED_CAST")
    override fun run(
        context: Context,
        input: TaskerInput<WriteDataInput>
    ): TaskerPluginResult<WriteDataOutput> = runCatching {
        Log.d(tag, "runner start: ${input.regular}")
        val repository = repositoryProvider(context)
        val clazz =
            Class.forName("$healthConnectRecordsPackage.${input.regular.recordType}")
        val records: List<Any> =
            serializer.toObjectList(input.regular.recordInput, clazz, listOf("metadata"))
        val result = runBlocking { repository.writeData(records as List<Record>) }
        val serializedResult = serializer.toString(result)
        Log.d(tag, "runner complete, result size: ${serializedResult.length}")
        TaskerPluginResultSucess(WriteDataOutput(healthConnectResult = serializedResult))
    }.getOrElse {
        Log.e(tag, "run error", it)
        TaskerPluginResultErrorWithOutput(errCode, it.toString())
    }
}
