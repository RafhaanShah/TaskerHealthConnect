package com.rafapps.taskerhealthconnect

import android.app.Application
import com.google.android.material.color.DynamicColors

class TaskerHealthConnectApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}
