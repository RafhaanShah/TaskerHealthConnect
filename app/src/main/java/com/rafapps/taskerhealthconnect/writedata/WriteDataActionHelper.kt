package com.rafapps.taskerhealthconnect.healthdata

import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfig
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfigHelper
import com.rafapps.taskerhealthconnect.writedata.WriteDataActionRunner
import com.rafapps.taskerhealthconnect.writedata.WriteDataInput
import com.rafapps.taskerhealthconnect.writedata.WriteDataOutput

class WriteDataActionHelper(config: TaskerPluginConfig<WriteDataInput>) :
    TaskerPluginConfigHelper<WriteDataInput, WriteDataOutput, WriteDataActionRunner>(config) {
    override val inputClass = WriteDataInput::class.java
    override val outputClass = WriteDataOutput::class.java
    override val runnerClass = WriteDataActionRunner::class.java
}
