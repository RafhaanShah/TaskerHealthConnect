package com.rafapps.taskerhealthconnect.getsteps

import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfig
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfigHelper

class GetStepsActionHelper(config: TaskerPluginConfig<GetStepsInput>) :
    TaskerPluginConfigHelper<GetStepsInput, GetStepsOutput, GetStepsActionRunner>(config) {
    override val inputClass = GetStepsInput::class.java
    override val outputClass = GetStepsOutput::class.java
    override val runnerClass = GetStepsActionRunner::class.java
}
