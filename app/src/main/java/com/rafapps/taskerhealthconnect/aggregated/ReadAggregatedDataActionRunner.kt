package com.rafapps.taskerhealthconnect.aggregated

import android.content.Context
import android.util.Log
import androidx.health.connect.client.aggregate.AggregateMetric
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

class ReadAggregatedDataActionRunner(
    private val repositoryProvider: HealthConnectRepositoryProvider = { HealthConnectRepository(it) },
    private val serializer: Serializer = Serializer()
) :
    TaskerPluginRunnerAction<ReadAggregatedDataInput, ReadAggregatedDataOutput>() {

    private val tag = "ReadAggregatedDataActionRunner"
    private val errCode = 1

    override val notificationProperties
        get() = NotificationProperties(iconResId = R.drawable.ic_launcher_foreground)

    override fun run(
        context: Context,
        input: TaskerInput<ReadAggregatedDataInput>
    ): TaskerPluginResult<ReadAggregatedDataOutput> {
        Log.d(tag, "run: ${input.regular}")
        val repository = repositoryProvider(context)
        val startTime = runCatching { Instant.ofEpochMilli(input.regular.startTime.toLong()) }.getOrElse { e ->
            Log.e(tag, "invalid input: ${input.regular.startTime}")
            return TaskerPluginResultErrorWithOutput(errCode, e.toString())
        }

        val endTime = runCatching { Instant.ofEpochMilli(input.regular.endTime.toLong()) }.getOrElse { e ->
            Log.e(tag, "invalid input: ${input.regular.endTime}")
            return TaskerPluginResultErrorWithOutput(errCode, e.toString())
        }

        return try {
            val split = input.regular.aggregateMetric.split(".")
            val clazz = Class.forName("$healthConnectRecordsPackage.${split[0]}")
            val field = clazz.getField(split[1])
            val metric = field.get(null) as AggregateMetric<*>
            val data = runBlocking { repository.readAggregatedData(metric, startTime, endTime) }
            val serializedResult = serializer.toString(data)
            TaskerPluginResultSucess(ReadAggregatedDataOutput(healthConnectResult = serializedResult))
        } catch (e: Exception) {
            Log.e(tag, "run error", e)
            TaskerPluginResultErrorWithOutput(errCode, e.toString())
        }
    }
}
