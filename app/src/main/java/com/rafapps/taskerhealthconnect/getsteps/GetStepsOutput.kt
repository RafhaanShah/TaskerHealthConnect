package com.rafapps.taskerhealthconnect.getsteps

import android.annotation.SuppressLint
import com.joaomgcd.taskerpluginlibrary.output.TaskerOutputObject
import com.joaomgcd.taskerpluginlibrary.output.TaskerOutputVariable
import com.rafapps.taskerhealthconnect.R

@SuppressLint("NonConstantResourceId")
@TaskerOutputObject
class GetStepsOutput(
    @get:TaskerOutputVariable(
        name = VARIABLE_NAME_STEPS,
        labelResId = R.string.steps,
        htmlLabelResId = R.string.steps
    ) val steps: Long = 0L
)
