package com.rafapps.taskerhealthconnect.aggregated

import android.annotation.SuppressLint
import com.joaomgcd.taskerpluginlibrary.input.TaskerInputField
import com.joaomgcd.taskerpluginlibrary.input.TaskerInputRoot
import com.rafapps.taskerhealthconnect.R

@SuppressLint("NonConstantResourceId") // TODO: check with nonFinalResIds
@TaskerInputRoot
class AggregatedHealthDataInput @JvmOverloads constructor(
    @field:TaskerInputField(
        key = "days",
        labelResId = R.string.days,
        descriptionResId = R.string.days_description
    ) var days: Long = 0L
)
