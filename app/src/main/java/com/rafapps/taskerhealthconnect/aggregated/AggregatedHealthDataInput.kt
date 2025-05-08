package com.rafapps.taskerhealthconnect.aggregated

import android.annotation.SuppressLint
import com.joaomgcd.taskerpluginlibrary.input.TaskerInputField
import com.joaomgcd.taskerpluginlibrary.input.TaskerInputRoot
import com.rafapps.taskerhealthconnect.R
import java.time.Instant

@SuppressLint("NonConstantResourceId") // TODO: check with nonFinalResIds
@TaskerInputRoot
class AggregatedHealthDataInput @JvmOverloads constructor(
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
