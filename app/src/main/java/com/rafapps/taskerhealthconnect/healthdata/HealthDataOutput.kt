package com.rafapps.taskerhealthconnect.healthdata

import android.annotation.SuppressLint
import com.joaomgcd.taskerpluginlibrary.output.TaskerOutputObject
import com.joaomgcd.taskerpluginlibrary.output.TaskerOutputVariable
import com.rafapps.taskerhealthconnect.R

@SuppressLint("NonConstantResourceId") // TODO: check with nonFinalResIds
@TaskerOutputObject
class HealthDataOutput(
    @get:TaskerOutputVariable(
        name = "healthData",
        labelResId = R.string.health_data,
        htmlLabelResId = R.string.health_data_description
    ) val healthData: String = "[]"
) {
    override fun toString(): String {
        return "healthData: $healthData"
    }
}
