package com.rafapps.taskerhealthconnect.aggregated

import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfig
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfigHelper

class AggregatedHealthDataActionHelper(config: TaskerPluginConfig<AggregatedHealthDataInput>) :
    TaskerPluginConfigHelper<AggregatedHealthDataInput, AggregatedHealthDataOutput, AggregatedHealthDataActionRunner>(config) {
    override val inputClass = AggregatedHealthDataInput::class.java
    override val outputClass = AggregatedHealthDataOutput::class.java
    override val runnerClass = AggregatedHealthDataActionRunner::class.java
}
