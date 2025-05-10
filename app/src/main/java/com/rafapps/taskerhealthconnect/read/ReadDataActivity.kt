package com.rafapps.taskerhealthconnect.read

import android.view.LayoutInflater
import android.view.View
import com.joaomgcd.taskerpluginlibrary.input.TaskerInput
import com.rafapps.taskerhealthconnect.TaskerConfigActivity
import com.rafapps.taskerhealthconnect.databinding.LayoutReadDataBinding
import com.rafapps.taskerhealthconnect.write.WriteDataActionRunner
import com.rafapps.taskerhealthconnect.write.WriteDataInput
import java.time.Instant

class ReadDataActivity : TaskerConfigActivity<ReadDataInput, ReadDataConfigHelper>() {

    private lateinit var binding: LayoutReadDataBinding
    private val runner by lazy { ReadDataActionRunner({ repository }) }

    override val tag = "ReadDataActivity"
    override val requiredPermissions: Set<String> = repository.readPermissions
    override val taskerHelper by lazy { ReadDataConfigHelper(this) }
    override val inputForTasker: TaskerInput<ReadDataInput>
        get() = TaskerInput(
            ReadDataInput(
                recordType = getInputRecordType(),
                startTime = getInputStartTime(),
                endTime = getInputEndTime()
            )
        )

    override fun provideContentView(layoutInflater: LayoutInflater): View {
        binding = LayoutReadDataBinding.inflate(layoutInflater)
        return binding.root
    }

    override suspend fun runDebugAction(): Any {
        val recordType = getInputRecordType()
        val startTime = getInputStartTime()
        val endTime = getInputEndTime()
        return runner.run(context, TaskerInput(ReadDataInput(recordType, startTime, endTime)))
    }

    override fun assignFromInput(input: TaskerInput<ReadDataInput>) {
        binding.recordTypeText.editText?.setText(input.regular.recordType)
        binding.startTimeText.editText?.setText(input.regular.startTime)
        binding.endTimeText.editText?.setText(input.regular.endTime)
    }

    private fun getInputRecordType(): String = binding.recordTypeText.editText?.text.toString()

    private fun getInputStartTime(): String = binding.startTimeText.editText?.text.toString()

    private fun getInputEndTime(): String = binding.endTimeText.editText?.text.toString()

}
