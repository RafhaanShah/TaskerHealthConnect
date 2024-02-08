package com.rafapps.taskerhealthconnect.tasker

import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfig
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfigHelper

class GetHealthDataActionHelper(config: TaskerPluginConfig<GetHealthDataInput>) :
    TaskerPluginConfigHelper<GetHealthDataInput, GetHealthDataOutput, GetHealthDataActionRunner>(config) {
    override val inputClass = GetHealthDataInput::class.java
    override val outputClass = GetHealthDataOutput::class.java
    override val runnerClass = GetHealthDataActionRunner::class.java
}
