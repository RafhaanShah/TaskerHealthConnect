package com.rafapps.taskerhealthconnect

import androidx.health.connect.client.records.ActiveCaloriesBurnedRecord
import androidx.health.connect.client.records.BasalBodyTemperatureRecord
import androidx.health.connect.client.records.BasalMetabolicRateRecord
import androidx.health.connect.client.records.BloodGlucoseRecord
import androidx.health.connect.client.records.BloodPressureRecord
import androidx.health.connect.client.records.BodyFatRecord
import androidx.health.connect.client.records.BodyTemperatureRecord
import androidx.health.connect.client.records.BodyWaterMassRecord
import androidx.health.connect.client.records.BoneMassRecord
import androidx.health.connect.client.records.CervicalMucusRecord
import androidx.health.connect.client.records.CyclingPedalingCadenceRecord
import androidx.health.connect.client.records.DistanceRecord
import androidx.health.connect.client.records.ElevationGainedRecord
import androidx.health.connect.client.records.ExerciseSessionRecord
import androidx.health.connect.client.records.FloorsClimbedRecord
import androidx.health.connect.client.records.HeartRateRecord
import androidx.health.connect.client.records.HeartRateVariabilityRmssdRecord
import androidx.health.connect.client.records.HeightRecord
import androidx.health.connect.client.records.HydrationRecord
import androidx.health.connect.client.records.IntermenstrualBleedingRecord
import androidx.health.connect.client.records.LeanBodyMassRecord
import androidx.health.connect.client.records.MenstruationFlowRecord
import androidx.health.connect.client.records.MenstruationPeriodRecord
import androidx.health.connect.client.records.NutritionRecord
import androidx.health.connect.client.records.OvulationTestRecord
import androidx.health.connect.client.records.OxygenSaturationRecord
import androidx.health.connect.client.records.PlannedExerciseSessionRecord
import androidx.health.connect.client.records.PowerRecord
import androidx.health.connect.client.records.Record
import androidx.health.connect.client.records.RespiratoryRateRecord
import androidx.health.connect.client.records.RestingHeartRateRecord
import androidx.health.connect.client.records.SexualActivityRecord
import androidx.health.connect.client.records.SkinTemperatureRecord
import androidx.health.connect.client.records.SleepSessionRecord
import androidx.health.connect.client.records.SpeedRecord
import androidx.health.connect.client.records.StepsCadenceRecord
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.records.TotalCaloriesBurnedRecord
import androidx.health.connect.client.records.Vo2MaxRecord
import androidx.health.connect.client.records.WeightRecord
import androidx.health.connect.client.records.WheelchairPushesRecord
import kotlin.reflect.KClass


val recordTypes: Set<KClass<out Record>> = setOf(
    ActiveCaloriesBurnedRecord::class,
    BasalBodyTemperatureRecord::class,
    BasalMetabolicRateRecord::class,
    BloodGlucoseRecord::class,
    BloodPressureRecord::class,
    BodyFatRecord::class,
    BodyTemperatureRecord::class,
    BodyWaterMassRecord::class,
    BoneMassRecord::class,
    CervicalMucusRecord::class,
    CyclingPedalingCadenceRecord::class,
    DistanceRecord::class,
    ElevationGainedRecord::class,
    ExerciseSessionRecord::class,
    FloorsClimbedRecord::class,
    HeartRateRecord::class,
    HeartRateVariabilityRmssdRecord::class,
    HeightRecord::class,
    HydrationRecord::class,
    IntermenstrualBleedingRecord::class,
    LeanBodyMassRecord::class,
    MenstruationFlowRecord::class,
    MenstruationPeriodRecord::class,
    NutritionRecord::class,
    OvulationTestRecord::class,
    OxygenSaturationRecord::class,
    PlannedExerciseSessionRecord::class,
    PowerRecord::class,
    RespiratoryRateRecord::class,
    RestingHeartRateRecord::class,
    SexualActivityRecord::class,
    SkinTemperatureRecord::class,
    SleepSessionRecord::class,
    SpeedRecord::class,
    StepsCadenceRecord::class,
    StepsRecord::class,
    TotalCaloriesBurnedRecord::class,
    Vo2MaxRecord::class,
    WeightRecord::class,
    WheelchairPushesRecord::class,
)

const val healthConnectRecordsPackage = "androidx.health.connect.client.records"
