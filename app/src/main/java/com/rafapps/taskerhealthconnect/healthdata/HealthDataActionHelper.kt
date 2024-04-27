package com.rafapps.taskerhealthconnect.healthdata

import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfig
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfigHelper

class HealthDataActionHelper(config: TaskerPluginConfig<HealthDataInput>) :
    TaskerPluginConfigHelper<HealthDataInput, HealthDataOutput, HealthDataActionRunner>(config) {
    override val inputClass = HealthDataInput::class.java
    override val outputClass = HealthDataOutput::class.java
    override val runnerClass = HealthDataActionRunner::class.java
}
