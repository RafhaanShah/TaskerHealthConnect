package com.rafapps.taskerhealthconnect.read

import android.annotation.SuppressLint
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfig
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfigHelper
import com.joaomgcd.taskerpluginlibrary.input.TaskerInputField
import com.joaomgcd.taskerpluginlibrary.input.TaskerInputRoot
import com.joaomgcd.taskerpluginlibrary.output.TaskerOutputObject
import com.joaomgcd.taskerpluginlibrary.output.TaskerOutputVariable
import com.rafapps.taskerhealthconnect.R
import java.time.Instant

class HealthDataActionHelper(config: TaskerPluginConfig<ReadDataInput>) :
    TaskerPluginConfigHelper<ReadDataInput, ReadDataOutput, ReadDataActionRunner>(config) {
    override val inputClass = ReadDataInput::class.java
    override val outputClass = ReadDataOutput::class.java
    override val runnerClass = ReadDataActionRunner::class.java
}
@SuppressLint("NonConstantResourceId") // TODO: check with nonFinalResIds
@TaskerInputRoot
class ReadDataInput @JvmOverloads constructor(
    @field:TaskerInputField(
        key = "recordType",
        labelResId = R.string.record_type,
        descriptionResId = R.string.record_type_description
    ) var recordType: String = "Record",
    @field:TaskerInputField(
        key = "startTime",
        labelResId = R.string.start_time_milliseconds,
        descriptionResId = R.string.start_time_milliseconds_description
    ) var startTime: String = Instant.now()
        .minusSeconds(60 * 60 * 24)
        .toEpochMilli()
        .toString(),
    @field:TaskerInputField(
        key = "endTime",
        labelResId = R.string.end_time_milliseconds,
        descriptionResId = R.string.end_time_milliseconds_description
    ) var endTime: String = Instant.now()
        .toEpochMilli()
        .toString()
) {
    override fun toString(): String {
        return "recordType: $recordType, startTime: $startTime, endTime: $endTime"
    }
}

@SuppressLint("NonConstantResourceId") // TODO: check with nonFinalResIds
@TaskerOutputObject
class ReadDataOutput(
    @get:TaskerOutputVariable(
        name = "healthConnectResult",
        labelResId = R.string.read_data,
        htmlLabelResId = R.string.read_data_description
    ) val healthConnectResult: String = "{}"
) {
    override fun toString(): String {
        return "healthConnectResult: $healthConnectResult"
    }
}
