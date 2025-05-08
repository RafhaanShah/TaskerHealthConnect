package com.rafapps.taskerhealthconnect.aggregated

import android.annotation.SuppressLint
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfig
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfigHelper
import com.joaomgcd.taskerpluginlibrary.input.TaskerInputField
import com.joaomgcd.taskerpluginlibrary.input.TaskerInputRoot
import com.joaomgcd.taskerpluginlibrary.output.TaskerOutputObject
import com.joaomgcd.taskerpluginlibrary.output.TaskerOutputVariable
import com.rafapps.taskerhealthconnect.R
import java.time.Instant

class ReadAggregatedDataHelper(config: TaskerPluginConfig<ReadAggregatedDataInput>) :
    TaskerPluginConfigHelper<ReadAggregatedDataInput, ReadAggregatedDataOutput, ReadAggregatedDataActionRunner>(config) {
    override val inputClass = ReadAggregatedDataInput::class.java
    override val outputClass = ReadAggregatedDataOutput::class.java
    override val runnerClass = ReadAggregatedDataActionRunner::class.java
}

@SuppressLint("NonConstantResourceId") // TODO: check with nonFinalResIds
@TaskerInputRoot
class ReadAggregatedDataInput @JvmOverloads constructor(
    @field:TaskerInputField(
        key = "aggregateMetric",
        labelResId = R.string.aggregate_metric,
        descriptionResId = R.string.aggregate_metric_description
    ) var aggregateMetric: String = "Record.COUNT_TOTAL",
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
        return "aggregateMetric: $aggregateMetric, startTime: $startTime, endTime: $endTime"
    }
}

@SuppressLint("NonConstantResourceId") // TODO: check with nonFinalResIds
@TaskerOutputObject
class ReadAggregatedDataOutput(
    @get:TaskerOutputVariable(
        name = "healthConnectResult",
        labelResId = R.string.read_aggregated_data,
        htmlLabelResId = R.string.read_aggregated_data_description
    ) val healthConnectResult: String = "{}"
) {
    override fun toString(): String {
        return "healthConnectResult: $healthConnectResult"
    }
}
