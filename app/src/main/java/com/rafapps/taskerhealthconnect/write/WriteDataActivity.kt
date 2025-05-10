package com.rafapps.taskerhealthconnect.write

import android.view.LayoutInflater
import android.view.View
import com.joaomgcd.taskerpluginlibrary.input.TaskerInput
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResult
import com.rafapps.taskerhealthconnect.TaskerConfigActivity
import com.rafapps.taskerhealthconnect.databinding.LayoutWriteDataBinding

class WriteDataActivity : TaskerConfigActivity<WriteDataInput, WriteDataConfigHelper>() {

    private lateinit var binding: LayoutWriteDataBinding
    private val runner by lazy { WriteDataActionRunner({ repository }) }

    override val tag = "WriteDataActivity"
    override val requiredPermissions: Set<String> by lazy { repository.writePermissions }
    override val taskerHelper by lazy { WriteDataConfigHelper(this) }
    override val inputForTasker: TaskerInput<WriteDataInput>
        get() = TaskerInput(
            WriteDataInput(
                recordType = getInputRecordType(),
                recordInput = getInputRecord()
            )
        )

    override fun provideContentView(layoutInflater: LayoutInflater): View {
        binding = LayoutWriteDataBinding.inflate(layoutInflater)
        return binding.root
    }

    override suspend fun runDebugAction(): TaskerPluginResult<*> {
        val recordType = getInputRecordType()
        val recordInput = getInputRecord()
        return runner.run(context, TaskerInput(WriteDataInput(recordType, recordInput)))
    }

    override fun assignFromInput(input: TaskerInput<WriteDataInput>) {
        binding.recordTypeText.editText?.setText(input.regular.recordType)
        binding.recordText.editText?.setText(input.regular.recordInput)
    }

    private fun getInputRecordType(): String = binding.recordTypeText.editText?.text.toString()

    private fun getInputRecord(): String = binding.recordText.editText?.text.toString()

}
