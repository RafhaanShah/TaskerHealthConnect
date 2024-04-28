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
import androidx.health.connect.client.records.LeanBodyMassRecord
import androidx.health.connect.client.records.MenstruationFlowRecord
import androidx.health.connect.client.records.NutritionRecord
import androidx.health.connect.client.records.OvulationTestRecord
import androidx.health.connect.client.records.OxygenSaturationRecord
import androidx.health.connect.client.records.PowerRecord
import androidx.health.connect.client.records.RespiratoryRateRecord
import androidx.health.connect.client.records.RestingHeartRateRecord
import androidx.health.connect.client.records.SexualActivityRecord
import androidx.health.connect.client.records.SleepSessionRecord
import androidx.health.connect.client.records.SpeedRecord
import androidx.health.connect.client.records.StepsCadenceRecord
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.records.TotalCaloriesBurnedRecord
import androidx.health.connect.client.records.Vo2MaxRecord
import androidx.health.connect.client.records.WeightRecord
import androidx.health.connect.client.records.WheelchairPushesRecord


// record types that do not support aggregations
val singleRecordTypes = setOf(
    BasalBodyTemperatureRecord::class,
    BloodGlucoseRecord::class,
    BloodPressureRecord::class,
    BodyFatRecord::class,
    BodyTemperatureRecord::class,
    BodyWaterMassRecord::class,
    BoneMassRecord::class,
    CervicalMucusRecord::class,
    CyclingPedalingCadenceRecord::class,
    HeartRateVariabilityRmssdRecord::class,
    LeanBodyMassRecord::class,
    MenstruationFlowRecord::class,
    OvulationTestRecord::class,
    OxygenSaturationRecord::class,
    RespiratoryRateRecord::class,
    SexualActivityRecord::class,
    SpeedRecord::class,
    StepsCadenceRecord::class,
    Vo2MaxRecord::class,
)

// record types that do support aggregations
val aggregateRecordTypes = setOf(
    ActiveCaloriesBurnedRecord::class,
    BasalMetabolicRateRecord::class,
    DistanceRecord::class,
    ElevationGainedRecord::class,
    ExerciseSessionRecord::class,
    FloorsClimbedRecord::class,
    HeartRateRecord::class,
    HeightRecord::class,
    HydrationRecord::class,
    NutritionRecord::class,
    PowerRecord::class,
    RestingHeartRateRecord::class,
    SleepSessionRecord::class,
    StepsRecord::class,
    TotalCaloriesBurnedRecord::class,
    WeightRecord::class,
    WheelchairPushesRecord::class,
)

// fields common to most record types
val commonFields = setOf("time", "startTime", "endTime")

// fields that return a list of records
val listFields = setOf("samples")

// fields used by list record values
val listValueFields = setOf(
    "beatsPerMinute",
    "power",
    "rate",
    "revolutionsPerMinute",
    "speed",
)

// fields that return a value for a record
val recordValueFields = setOf(
    "appearance",
    "count",
    "diastolic",
    "flow",
    "heartRateVariabilityMillis",
    "level",
    "mass",
    "mealType",
    "measurementLocation",
    "percentage",
    "protectionUsed",
    "rate",
    "relationToMeal",
    "result",
    "sensation",
    "specimenSource",
    "systolic",
    "temperature",
    "vo2MillilitersPerMinuteKilogram",
)

// from androidx/health/connect/client/impl/platform/records/AggregationMappings
val doubleAggregations = mapOf(
    "FloorsClimbedRecord_FLOORS_CLIMBED_TOTAL" to FloorsClimbedRecord.FLOORS_CLIMBED_TOTAL,
)

val durationAggregations = setOf(
    "ExerciseSessionRecord_EXERCISE_DURATION_TOTAL" to ExerciseSessionRecord.EXERCISE_DURATION_TOTAL,
    "SleepSessionRecord_SLEEP_DURATION_TOTAL" to SleepSessionRecord.SLEEP_DURATION_TOTAL,
)

val energyAggregations = setOf(
    "ActiveCaloriesBurnedRecord_ACTIVE_CALORIES_TOTAL" to ActiveCaloriesBurnedRecord.ACTIVE_CALORIES_TOTAL,
    "BasalMetabolicRateRecord_BASAL_CALORIES_TOTAL" to BasalMetabolicRateRecord.BASAL_CALORIES_TOTAL,
    "NutritionRecord_ENERGY_TOTAL" to NutritionRecord.ENERGY_TOTAL,
    "NutritionRecord_ENERGY_FROM_FAT_TOTAL" to NutritionRecord.ENERGY_FROM_FAT_TOTAL,
    "TotalCaloriesBurnedRecord_ENERGY_TOTAL" to TotalCaloriesBurnedRecord.ENERGY_TOTAL,
)

val lengthAggregations = mapOf(
    "DistanceRecord_DISTANCE_TOTAL" to DistanceRecord.DISTANCE_TOTAL,
    "ElevationGainedRecord_ELEVATION_GAINED_TOTAL" to ElevationGainedRecord.ELEVATION_GAINED_TOTAL,
    "HeightRecord_HEIGHT_AVG" to HeightRecord.HEIGHT_AVG,
    "HeightRecord_HEIGHT_MIN" to HeightRecord.HEIGHT_MIN,
    "HeightRecord_HEIGHT_MAX" to HeightRecord.HEIGHT_MAX,
)

val longAggregations = mapOf(
    "HeartRateRecord_BPM_AVG" to HeartRateRecord.BPM_AVG,
    "HeartRateRecord_BPM_MIN" to HeartRateRecord.BPM_MIN,
    "HeartRateRecord_BPM_MAX" to HeartRateRecord.BPM_MAX,
    "HeartRateRecord_MEASUREMENTS_COUNT" to HeartRateRecord.MEASUREMENTS_COUNT,
    "RestingHeartRateRecord_BPM_AVG" to RestingHeartRateRecord.BPM_AVG,
    "RestingHeartRateRecord_BPM_MIN" to RestingHeartRateRecord.BPM_MIN,
    "RestingHeartRateRecord_BPM_MAX" to RestingHeartRateRecord.BPM_MAX,
    "StepsRecord_COUNT_TOTAL" to StepsRecord.COUNT_TOTAL,
    "WheelchairPushesRecord_COUNT_TOTAL" to WheelchairPushesRecord.COUNT_TOTAL
)

val gramsAggregations = mapOf(
    "NutritionRecord_ENERGY_TOTAL" to NutritionRecord.ENERGY_TOTAL,
    "NutritionRecord_ENERGY_FROM_FAT_TOTAL" to NutritionRecord.ENERGY_FROM_FAT_TOTAL,
    "NutritionRecord_BIOTIN_TOTAL" to NutritionRecord.BIOTIN_TOTAL,
    "NutritionRecord_CAFFEINE_TOTAL" to NutritionRecord.CAFFEINE_TOTAL,
    "NutritionRecord_CALCIUM_TOTAL" to NutritionRecord.CALCIUM_TOTAL,
    "NutritionRecord_CHLORIDE_TOTAL" to NutritionRecord.CHLORIDE_TOTAL,
    "NutritionRecord_CHOLESTEROL_TOTAL" to NutritionRecord.CHOLESTEROL_TOTAL,
    "NutritionRecord_CHROMIUM_TOTAL" to NutritionRecord.CHROMIUM_TOTAL,
    "NutritionRecord_COPPER_TOTAL" to NutritionRecord.COPPER_TOTAL,
    "NutritionRecord_DIETARY_FIBER_TOTAL" to NutritionRecord.DIETARY_FIBER_TOTAL,
    "NutritionRecord_FOLATE_TOTAL" to NutritionRecord.FOLATE_TOTAL,
    "NutritionRecord_FOLIC_ACID_TOTAL" to NutritionRecord.FOLIC_ACID_TOTAL,
    "NutritionRecord_IODINE_TOTAL" to NutritionRecord.IODINE_TOTAL,
    "NutritionRecord_IRON_TOTAL" to NutritionRecord.IRON_TOTAL,
    "NutritionRecord_MAGNESIUM_TOTAL" to NutritionRecord.MAGNESIUM_TOTAL,
    "NutritionRecord_MANGANESE_TOTAL" to NutritionRecord.MANGANESE_TOTAL,
    "NutritionRecord_MOLYBDENUM_TOTAL" to NutritionRecord.MOLYBDENUM_TOTAL,
    "NutritionRecord_MONOUNSATURATED_FAT_TOTAL" to NutritionRecord.MONOUNSATURATED_FAT_TOTAL,
    "NutritionRecord_NIACIN_TOTAL" to NutritionRecord.NIACIN_TOTAL,
    "NutritionRecord_PANTOTHENIC_ACID_TOTAL" to NutritionRecord.PANTOTHENIC_ACID_TOTAL,
    "NutritionRecord_PHOSPHORUS_TOTAL" to NutritionRecord.PHOSPHORUS_TOTAL,
    "NutritionRecord_POLYUNSATURATED_FAT_TOTAL" to NutritionRecord.POLYUNSATURATED_FAT_TOTAL,
    "NutritionRecord_POTASSIUM_TOTAL" to NutritionRecord.POTASSIUM_TOTAL,
    "NutritionRecord_PROTEIN_TOTAL" to NutritionRecord.PROTEIN_TOTAL,
    "NutritionRecord_RIBOFLAVIN_TOTAL" to NutritionRecord.RIBOFLAVIN_TOTAL,
    "NutritionRecord_SATURATED_FAT_TOTAL" to NutritionRecord.SATURATED_FAT_TOTAL,
    "NutritionRecord_SELENIUM_TOTAL" to NutritionRecord.SELENIUM_TOTAL,
    "NutritionRecord_SODIUM_TOTAL" to NutritionRecord.SODIUM_TOTAL,
    "NutritionRecord_SUGAR_TOTAL" to NutritionRecord.SUGAR_TOTAL,
    "NutritionRecord_THIAMIN_TOTAL" to NutritionRecord.THIAMIN_TOTAL,
    "NutritionRecord_TOTAL_CARBOHYDRATE_TOTAL" to NutritionRecord.TOTAL_CARBOHYDRATE_TOTAL,
    "NutritionRecord_TOTAL_FAT_TOTAL" to NutritionRecord.TOTAL_FAT_TOTAL,
    "NutritionRecord_UNSATURATED_FAT_TOTAL" to NutritionRecord.UNSATURATED_FAT_TOTAL,
    "NutritionRecord_VITAMIN_A_TOTAL" to NutritionRecord.VITAMIN_A_TOTAL,
    "NutritionRecord_VITAMIN_B12_TOTAL" to NutritionRecord.VITAMIN_B12_TOTAL,
    "NutritionRecord_VITAMIN_B6_TOTAL" to NutritionRecord.VITAMIN_B6_TOTAL,
    "NutritionRecord_VITAMIN_C_TOTAL" to NutritionRecord.VITAMIN_C_TOTAL,
    "NutritionRecord_VITAMIN_D_TOTAL" to NutritionRecord.VITAMIN_D_TOTAL,
    "NutritionRecord_VITAMIN_E_TOTAL" to NutritionRecord.VITAMIN_E_TOTAL,
    "NutritionRecord_VITAMIN_K_TOTAL" to NutritionRecord.VITAMIN_K_TOTAL,
    "NutritionRecord_ZINC_TOTAL" to NutritionRecord.ZINC_TOTAL
)

val kilogramsAggregations = mapOf(
    "WeightRecord_WEIGHT_AVG" to WeightRecord.WEIGHT_AVG,
    "WeightRecord_WEIGHT_MIN" to WeightRecord.WEIGHT_MIN,
    "WeightRecord_WEIGHT_MAX" to WeightRecord.WEIGHT_MAX,
)

val powerAggregations = mapOf(
    "PowerRecord_POWER_AVG" to PowerRecord.POWER_AVG,
    "PowerRecord_POWER_MAX" to PowerRecord.POWER_MAX,
    "PowerRecord_POWER_MIN" to PowerRecord.POWER_MIN,
)

val volumeAggregations = mapOf(
    "HydrationRecord_VOLUME_TOTAL" to HydrationRecord.VOLUME_TOTAL,
)

val aggregateMetricTypes =
    doubleAggregations + durationAggregations + energyAggregations + lengthAggregations +
            longAggregations + gramsAggregations + kilogramsAggregations + powerAggregations +
            volumeAggregations
