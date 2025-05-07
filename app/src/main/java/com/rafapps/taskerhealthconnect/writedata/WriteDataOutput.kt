package com.rafapps.taskerhealthconnect.writedata

import android.annotation.SuppressLint
import com.joaomgcd.taskerpluginlibrary.output.TaskerOutputObject
import com.joaomgcd.taskerpluginlibrary.output.TaskerOutputVariable
import com.rafapps.taskerhealthconnect.R

@SuppressLint("NonConstantResourceId") // TODO: check with nonFinalResIds
@TaskerOutputObject
class WriteDataOutput(
    @get:TaskerOutputVariable(
        name = "recordIds",
        labelResId = R.string.record_ids,
        htmlLabelResId = R.string.record_ids_description
    ) val recordIds: String = "[]"
) {
    override fun toString(): String {
        return "recordIds: $recordIds"
    }
}
