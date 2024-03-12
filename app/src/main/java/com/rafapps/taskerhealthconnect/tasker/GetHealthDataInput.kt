package com.rafapps.taskerhealthconnect.tasker

import android.annotation.SuppressLint
import com.joaomgcd.taskerpluginlibrary.input.TaskerInputField
import com.joaomgcd.taskerpluginlibrary.input.TaskerInputRoot
import com.rafapps.taskerhealthconnect.R

@SuppressLint("NonConstantResourceId")
@TaskerInputRoot
class GetHealthDataInput @JvmOverloads constructor(
    @field:TaskerInputField(
        key = VARIABLE_NAME_DAYS,
        labelResId = R.string.days,
        descriptionResId = R.string.days_description
    ) var days: Long = 0L
)
