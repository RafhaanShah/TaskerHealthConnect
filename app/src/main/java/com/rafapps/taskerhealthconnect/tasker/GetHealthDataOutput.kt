package com.rafapps.taskerhealthconnect.tasker

import android.annotation.SuppressLint
import com.joaomgcd.taskerpluginlibrary.output.TaskerOutputObject
import com.joaomgcd.taskerpluginlibrary.output.TaskerOutputVariable
import com.rafapps.taskerhealthconnect.R

@SuppressLint("NonConstantResourceId")
@TaskerOutputObject
class GetHealthDataOutput(
    @get:TaskerOutputVariable(
        name = VARIABLE_NAME_DATA,
        labelResId = R.string.data,
        htmlLabelResId = R.string.data
    ) val data: String = ""
)
