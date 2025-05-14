package com.rafapps.taskerhealthconnect

import androidx.health.connect.client.HealthConnectFeatures
import androidx.health.connect.client.permission.HealthPermission

const val PERMISSION_PREFIX = "android.permission.health."

// needs a feature check before requesting permission
val featureBasedPermissions = mapOf(
    HealthPermission.PERMISSION_READ_HEALTH_DATA_IN_BACKGROUND to HealthConnectFeatures.FEATURE_READ_HEALTH_DATA_IN_BACKGROUND,
    PERMISSION_PREFIX + "READ_SKIN_TEMPERATURE" to HealthConnectFeatures.FEATURE_SKIN_TEMPERATURE,
    PERMISSION_PREFIX + "WRITE_SKIN_TEMPERATURE" to HealthConnectFeatures.FEATURE_SKIN_TEMPERATURE,
    PERMISSION_PREFIX + "READ_PLANNED_EXERCISE" to HealthConnectFeatures.FEATURE_PLANNED_EXERCISE,
    PERMISSION_PREFIX + "WRITE_PLANNED_EXERCISE" to HealthConnectFeatures.FEATURE_PLANNED_EXERCISE,
    HealthPermission.PERMISSION_READ_HEALTH_DATA_HISTORY to HealthConnectFeatures.FEATURE_READ_HEALTH_DATA_HISTORY,
)
