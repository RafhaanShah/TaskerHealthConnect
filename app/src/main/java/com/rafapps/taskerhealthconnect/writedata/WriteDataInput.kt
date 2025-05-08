package com.rafapps.taskerhealthconnect.writedata

import android.annotation.SuppressLint
import com.joaomgcd.taskerpluginlibrary.input.TaskerInputField
import com.joaomgcd.taskerpluginlibrary.input.TaskerInputRoot
import com.rafapps.taskerhealthconnect.R
import java.time.Instant

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
        labelResId = R.string.records_json,
        descriptionResId = R.string.records_json_description
    ) var recordsJson: String = ""
) {
    override fun toString(): String {
        return "recordType: $recordType"
    }
}
