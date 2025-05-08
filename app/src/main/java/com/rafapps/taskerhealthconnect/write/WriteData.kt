package com.rafapps.taskerhealthconnect.write

import android.annotation.SuppressLint
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfig
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfigHelper
import com.joaomgcd.taskerpluginlibrary.input.TaskerInputField
import com.joaomgcd.taskerpluginlibrary.input.TaskerInputRoot
import com.joaomgcd.taskerpluginlibrary.output.TaskerOutputObject
import com.joaomgcd.taskerpluginlibrary.output.TaskerOutputVariable
import com.rafapps.taskerhealthconnect.R

class WriteDataActionHelper(config: TaskerPluginConfig<WriteDataInput>) :
    TaskerPluginConfigHelper<WriteDataInput, WriteDataOutput, WriteDataActionRunner>(config) {
    override val inputClass = WriteDataInput::class.java
    override val outputClass = WriteDataOutput::class.java
    override val runnerClass = WriteDataActionRunner::class.java
}

@SuppressLint("NonConstantResourceId") // TODO: check with nonFinalResIds
@TaskerInputRoot
class WriteDataInput @JvmOverloads constructor(
    @field:TaskerInputField(
        key = "recordType",
        labelResId = R.string.record_type,
        descriptionResId = R.string.record_type_description
    ) var recordType: String = "Record",
    @field:TaskerInputField(
        key = "recordsJson",
        labelResId = R.string.write_data_input,
        descriptionResId = R.string.write_data_input_description
    ) var recordsJson: String = ""
) {
    override fun toString(): String {
        return "recordType: $recordType"
    }
}

@SuppressLint("NonConstantResourceId") // TODO: check with nonFinalResIds
@TaskerOutputObject
class WriteDataOutput(
    @get:TaskerOutputVariable(
        name = "healthConnectResult",
        labelResId = R.string.write_data_response,
        htmlLabelResId = R.string.write_data_response_description
    ) val healthConnectResult: String = "{}"
) {
    override fun toString(): String {
        return "healthConnectResult: $healthConnectResult"
    }
}
