package com.rafapps.taskerhealthconnect.tasker

import android.content.Context
import com.joaomgcd.taskerpluginlibrary.action.TaskerPluginRunnerAction
import com.joaomgcd.taskerpluginlibrary.input.TaskerInput
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResult
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResultSucess
import com.rafapps.taskerhealthconnect.HealthConnectRepository
import com.rafapps.taskerhealthconnect.R
import kotlinx.coroutines.runBlocking
import java.time.Instant
import java.time.ZonedDateTime

class GetHealthDataActionRunner : TaskerPluginRunnerAction<GetHealthDataInput, GetHealthDataOutput>() {
    override val notificationProperties
        get() = NotificationProperties(iconResId = R.drawable.ic_launcher_foreground)

    override fun run(
        context: Context,
        input: TaskerInput<GetHealthDataInput>
    ): TaskerPluginResult<GetHealthDataOutput> {
        val repository = HealthConnectRepository(context)
        val daysOffset = input.regular.days
        val now = Instant.now()
        val zonedDateTime = ZonedDateTime.now()
        val offsetTime = zonedDateTime.minusDays(daysOffset)
            .minusHours(zonedDateTime.hour.toLong())
            .minusMinutes(zonedDateTime.minute.toLong())
            .toInstant()

        val data = runBlocking { repository.getData(startTime = offsetTime, endTime = now) }
        return TaskerPluginResultSucess(GetHealthDataOutput(data))
    }
}
