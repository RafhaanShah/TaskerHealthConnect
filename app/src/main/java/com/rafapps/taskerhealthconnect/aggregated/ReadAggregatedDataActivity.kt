package com.rafapps.taskerhealthconnect.aggregated

import android.view.LayoutInflater
import android.view.View
import com.joaomgcd.taskerpluginlibrary.input.TaskerInput
import com.rafapps.taskerhealthconnect.TaskerConfigActivity
import com.rafapps.taskerhealthconnect.databinding.LayoutReadAggregatedDataBinding

class ReadAggregatedDataActivity :
    TaskerConfigActivity<ReadAggregatedDataInput, ReadAggregatedDataConfigHelper>() {

    private lateinit var binding: LayoutReadAggregatedDataBinding
    private val runner by lazy { ReadAggregatedDataActionRunner({ repository }) }

    override val tag = "ReadAggregatedDataActivity"
    override val requiredPermissions: Set<String> = repository.readPermissions
    override val taskerHelper by lazy { ReadAggregatedDataConfigHelper(this) }
    override val inputForTasker: TaskerInput<ReadAggregatedDataInput>
        get() = TaskerInput(
            ReadAggregatedDataInput(
                aggregateMetric = getInputAggregateMetric(),
                startTime = getInputStartTime(),
                endTime = getInputEndTime()
            )
        )

    override fun provideContentView(layoutInflater: LayoutInflater): View {
        binding = LayoutReadAggregatedDataBinding.inflate(layoutInflater)
        return binding.root
    }

    override suspend fun runDebugAction(): Any {
        val aggregateMetric = getInputAggregateMetric()
        val startTime = getInputStartTime()
        val endTime = getInputEndTime()
        return runner.run(
            context,
            TaskerInput(ReadAggregatedDataInput(aggregateMetric, startTime, endTime))
        )
    }

    override fun assignFromInput(input: TaskerInput<ReadAggregatedDataInput>) {
        binding.aggregateMetricText.editText?.setText(input.regular.aggregateMetric)
        binding.startTimeText.editText?.setText(input.regular.startTime)
        binding.endTimeText.editText?.setText(input.regular.endTime)
    }

    private fun getInputAggregateMetric(): String =
        binding.aggregateMetricText.editText?.text.toString()

    private fun getInputStartTime(): String = binding.startTimeText.editText?.text.toString()

    private fun getInputEndTime(): String = binding.endTimeText.editText?.text.toString()

}
