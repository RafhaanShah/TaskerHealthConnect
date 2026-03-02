package com.rafapps.taskerhealthconnect.delete

import android.view.LayoutInflater
import android.view.View
import com.joaomgcd.taskerpluginlibrary.input.TaskerInput
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResult
import com.rafapps.taskerhealthconnect.TaskerConfigActivity
import com.rafapps.taskerhealthconnect.databinding.LayoutDeleteDataBinding

class DeleteDataActivity : TaskerConfigActivity<DeleteDataInput, DeleteDataConfigHelper>() {

    private lateinit var binding: LayoutDeleteDataBinding
    private val runner by lazy { DeleteDataActionRunner { repository } }

    override val tag = "DeleteDataActivity"
    override val requiredPermissions: Set<String> by lazy { repository.writePermissions }
    override val taskerHelper by lazy { DeleteDataConfigHelper(this) }
    override val inputForTasker: TaskerInput<DeleteDataInput>
        get() = TaskerInput(
            DeleteDataInput(
                recordType = getInputRecordType(),
                startTime = getInputStartTime(),
                endTime = getInputEndTime()
            )
        )

    override fun provideContentView(layoutInflater: LayoutInflater): View {
        binding = LayoutDeleteDataBinding.inflate(layoutInflater)
        return binding.root
    }

    override suspend fun runDebugAction(): TaskerPluginResult<*> {
        val recordType = getInputRecordType()
        val startTime = getInputStartTime()
        val endTime = getInputEndTime()
        return runner.run(context, TaskerInput(DeleteDataInput(recordType, startTime, endTime)))
    }

    override fun assignFromInput(input: TaskerInput<DeleteDataInput>) {
        binding.recordTypeText.editText?.setText(input.regular.recordType)
        binding.startTimeText.editText?.setText(input.regular.startTime)
        binding.endTimeText.editText?.setText(input.regular.endTime)
    }

    private fun getInputRecordType(): String = binding.recordTypeText.editText?.text.toString()

    private fun getInputStartTime(): String = binding.startTimeText.editText?.text.toString()

    private fun getInputEndTime(): String = binding.endTimeText.editText?.text.toString()

}
