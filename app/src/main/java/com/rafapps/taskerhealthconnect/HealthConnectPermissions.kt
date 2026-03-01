package com.rafapps.taskerhealthconnect

import androidx.health.connect.client.HealthConnectFeatures
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.ActivityIntensityRecord
import androidx.health.connect.client.records.MindfulnessSessionRecord
import androidx.health.connect.client.records.PlannedExerciseSessionRecord
import androidx.health.connect.client.records.Record
import androidx.health.connect.client.records.SkinTemperatureRecord
import kotlin.reflect.KClass

// needs a feature check before requesting permission
val featureBasedPermissions: Map<KClass<out Record>, Int> = mapOf(
    ActivityIntensityRecord::class to HealthConnectFeatures.FEATURE_ACTIVITY_INTENSITY,
    PlannedExerciseSessionRecord::class to HealthConnectFeatures.FEATURE_PLANNED_EXERCISE,
    MindfulnessSessionRecord::class to HealthConnectFeatures.FEATURE_MINDFULNESS_SESSION,
    SkinTemperatureRecord::class to HealthConnectFeatures.FEATURE_SKIN_TEMPERATURE,
)

val backgroundPermissions = mapOf(
    HealthPermission.PERMISSION_READ_HEALTH_DATA_IN_BACKGROUND to HealthConnectFeatures.FEATURE_READ_HEALTH_DATA_IN_BACKGROUND,
    HealthPermission.PERMISSION_READ_HEALTH_DATA_HISTORY to HealthConnectFeatures.FEATURE_READ_HEALTH_DATA_HISTORY,
)
