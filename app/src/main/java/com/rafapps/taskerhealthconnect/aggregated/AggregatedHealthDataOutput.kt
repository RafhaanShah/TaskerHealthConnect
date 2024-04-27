package com.rafapps.taskerhealthconnect.aggregated

import android.annotation.SuppressLint
import com.joaomgcd.taskerpluginlibrary.output.TaskerOutputObject
import com.joaomgcd.taskerpluginlibrary.output.TaskerOutputVariable
import com.rafapps.taskerhealthconnect.R

@SuppressLint("NonConstantResourceId") // TODO: check with nonFinalResIds
@TaskerOutputObject
class AggregatedHealthDataOutput(
    @get:TaskerOutputVariable(
        name = "aggregatedHealthData",
        labelResId = R.string.aggregated_health_data,
        htmlLabelResId = R.string.aggregated_health_data_description
    ) val aggregatedHealthData: String = "[]"
) {
    override fun toString(): String {
        return "aggregatedHealthData: $aggregatedHealthData"
    }
}
