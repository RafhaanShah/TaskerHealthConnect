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
import androidx.health.connect.client.records.DistanceRecord
import androidx.health.connect.client.records.ElevationGainedRecord
import androidx.health.connect.client.records.ExerciseCompletionGoal
import androidx.health.connect.client.records.ExerciseLap
import androidx.health.connect.client.records.ExercisePerformanceTarget
import androidx.health.connect.client.records.ExerciseSegment
import androidx.health.connect.client.records.ExerciseSessionRecord
import androidx.health.connect.client.records.PlannedExerciseSessionRecord
import androidx.health.connect.client.records.FloorsClimbedRecord
import androidx.health.connect.client.records.HeartRateRecord
import androidx.health.connect.client.records.HeightRecord
import androidx.health.connect.client.records.HydrationRecord
import androidx.health.connect.client.records.IntermenstrualBleedingRecord
import androidx.health.connect.client.records.LeanBodyMassRecord
import androidx.health.connect.client.records.MenstruationFlowRecord
import androidx.health.connect.client.records.MenstruationPeriodRecord
import androidx.health.connect.client.records.NutritionRecord
import androidx.health.connect.client.records.OvulationTestRecord
import androidx.health.connect.client.records.OxygenSaturationRecord
import androidx.health.connect.client.records.PlannedExerciseBlock
import androidx.health.connect.client.records.PlannedExerciseStep
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
import androidx.health.connect.client.records.metadata.Metadata
import androidx.health.connect.client.units.BloodGlucose
import androidx.health.connect.client.units.Energy
import androidx.health.connect.client.units.Length
import androidx.health.connect.client.units.Mass
import androidx.health.connect.client.units.Percentage
import androidx.health.connect.client.units.Power
import androidx.health.connect.client.units.Pressure
import androidx.health.connect.client.units.Temperature
import androidx.health.connect.client.units.TemperatureDelta
import androidx.health.connect.client.units.Velocity
import androidx.health.connect.client.units.Volume
import java.time.Duration
import java.time.Instant
import java.time.ZoneOffset
import kotlin.reflect.KClass
import kotlin.reflect.full.companionObject

private val metadata = Metadata.manualEntry()

private val zoneOffset: ZoneOffset? = null
private val startZoneOffset = zoneOffset
private val endZoneOffset = zoneOffset
private val time = Instant.ofEpochMilli(1746800000000)
val startTime: Instant = time
val endTime: Instant = time.plusSeconds(60 * 60)
private val energy = Energy.calories(23.0)
private val temperature = Temperature.celsius(36.0)
private val baseline = temperature
private val delta = TemperatureDelta.celsius(1.0)
private val power = Power.watts(10.0)
private val basalMetabolicRate = power
private val bloodGlucose = BloodGlucose.milligramsPerDeciliter(2.0)
private val pressure = Pressure.millimetersOfMercury(25.0)
private val systolic = pressure
private val diastolic = pressure
private val percentage = Percentage(23.0)
private val mass = Mass.kilograms(50.0)
private val weight = mass
private val length = Length.meters(2.0)
private val elevation = length
private val distance = length
private val height = length
private val duration = Duration.ofMinutes(30)
private val speed = Velocity.kilometersPerHour(10.0)
private val volume = Volume.liters(2.0)

val testData: Map<KClass<out Record>, Record> = recordTypes.associateWith { kClass ->
    when (kClass) {
        ActiveCaloriesBurnedRecord::class -> ActiveCaloriesBurnedRecord(
            startTime = time,
            startZoneOffset = startZoneOffset,
            endTime = endTime,
            endZoneOffset = endZoneOffset,
            energy = energy,
            metadata = metadata
        )

        BasalBodyTemperatureRecord::class -> BasalBodyTemperatureRecord(
            time = time,
            zoneOffset = zoneOffset,
            metadata = metadata,
            temperature = temperature,
            measurementLocation = 0
        )

        BasalMetabolicRateRecord::class -> BasalMetabolicRateRecord(
            time = time,
            zoneOffset = zoneOffset,
            basalMetabolicRate = basalMetabolicRate,
            metadata = metadata
        )

        BloodGlucoseRecord::class -> BloodGlucoseRecord(
            time = time,
            zoneOffset = zoneOffset,
            metadata = metadata,
            level = bloodGlucose,
            specimenSource = 0,
            mealType = 0,
            relationToMeal = 0
        )

        BloodPressureRecord::class -> BloodPressureRecord(
            time = time,
            zoneOffset = zoneOffset,
            metadata = metadata,
            systolic = systolic,
            diastolic = diastolic,
            bodyPosition = 0,
            measurementLocation = 0
        )

        BodyFatRecord::class -> BodyFatRecord(
            metadata = metadata
        )

        BodyTemperatureRecord::class -> BodyTemperatureRecord(
            time = time,
            zoneOffset = zoneOffset,
            metadata = metadata,
            temperature = temperature,
            measurementLocation = 0
        )

        BodyWaterMassRecord::class -> BodyWaterMassRecord(
            time = time,
            zoneOffset = zoneOffset,
            mass = mass,
            time = time, zoneOffset = zoneOffset, mass = mass, metadata = metadata
        )

        BoneMassRecord::class -> BoneMassRecord(
            time = time,
            zoneOffset = zoneOffset,
            mass = mass,
            metadata = metadata
        )

        CervicalMucusRecord::class -> CervicalMucusRecord(
            metadata = metadata,
            appearance = 0,
            sensation = 0
        )

        CyclingPedalingCadenceRecord::class -> CyclingPedalingCadenceRecord(
            startTime = time,
            startZoneOffset = startZoneOffset,
            endTime = endTime,
            endZoneOffset = endZoneOffset,
            metadata = metadata,
            samples = listOf(
                CyclingPedalingCadenceRecord.Sample(
                    revolutionsPerMinute = 8.9
                )
            )
        )

        DistanceRecord::class -> DistanceRecord(
            startTime = time,
            startZoneOffset = startZoneOffset,
            endTime = endTime,
            endZoneOffset = endZoneOffset,
            metadata = metadata,
            distance = distance
        )

        ElevationGainedRecord::class -> ElevationGainedRecord(
            startTime = time,
            startZoneOffset = startZoneOffset,
            endTime = endTime,
            endZoneOffset = endZoneOffset,
            metadata = metadata,
            elevation = elevation
        )

        ExerciseSessionRecord::class -> ExerciseSessionRecord(
            startTime = time,
            startZoneOffset = startZoneOffset,
            endTime = endTime,
            endZoneOffset = endZoneOffset,
            metadata = metadata,
            exerciseType = 0,
            segments = listOf(
                ExerciseSegment(
                    startTime = time,
                    endTime = endTime,
                    segmentType = 0,
                )
            ),
            laps = listOf(ExerciseLap(startTime = time, endTime = endTime, length = length)),
            title = "Morning Run",
            notes = "Test Exercise Session Record"
        )

        FloorsClimbedRecord::class -> FloorsClimbedRecord(
            startTime = startTime,
            startZoneOffset = startZoneOffset,
            endTime = endTime,
            endZoneOffset = endZoneOffset,
            floors = 1.0,
            metadata = metadata
        )

        HeartRateRecord::class -> HeartRateRecord(
            startTime = startTime,
            startZoneOffset = startZoneOffset,
            endTime = endTime,
            endZoneOffset = endZoneOffset,
            samples = listOf(HeartRateRecord.Sample(time = time, beatsPerMinute = 60)),
            metadata = metadata,
        )

        HeightRecord::class -> HeightRecord(
            time = time,
            zoneOffset = zoneOffset,
            height = height,
            metadata = metadata
        )

        HeightRecord::class -> HeightRecord(
        HydrationRecord::class -> HydrationRecord(
            startTime = startTime,
            startZoneOffset = startZoneOffset,
            endTime = endTime,
            endZoneOffset = endZoneOffset,
            volume = volume,
            metadata = metadata
        )

        IntermenstrualBleedingRecord::class -> IntermenstrualBleedingRecord(
            time = time,
            zoneOffset = zoneOffset,
            time = time, zoneOffset = zoneOffset, metadata = metadata
        )

        LeanBodyMassRecord::class -> LeanBodyMassRecord(
            time = time,
            zoneOffset = zoneOffset,
            mass = mass,
            metadata = metadata
        )

        MenstruationFlowRecord::class -> MenstruationFlowRecord(
            flow = 1
        )

        MenstruationPeriodRecord::class -> MenstruationPeriodRecord(
            startTime = startTime,
            startZoneOffset = startZoneOffset,
            endTime = endTime,
            endZoneOffset = endZoneOffset,
            metadata = metadata,
        )

        NutritionRecord::class -> NutritionRecord(
            startTime = startTime,
            startZoneOffset = startZoneOffset,
            endTime = endTime,
            endZoneOffset = endZoneOffset,
            metadata = metadata,
            biotin = Mass.grams(1.0),
            caffeine = Mass.grams(1.0),
            calcium = Mass.grams(1.0),
            energy = energy,
            energyFromFat = energy,
            chloride = Mass.grams(1.0),
            cholesterol = Mass.grams(1.0),
            chromium = Mass.grams(1.0),
            copper = Mass.grams(1.0),
            dietaryFiber = Mass.grams(1.0),
            folate = Mass.grams(1.0),
            folicAcid = Mass.grams(1.0),
            iodine = Mass.grams(1.0),
            iron = Mass.grams(1.0),
            magnesium = Mass.grams(1.0),
            manganese = Mass.grams(1.0),
            molybdenum = Mass.grams(1.0),
            monounsaturatedFat = Mass.grams(1.0),
            niacin = Mass.grams(1.0),
            pantothenicAcid = Mass.grams(1.0),
            phosphorus = Mass.grams(1.0),
            polyunsaturatedFat = Mass.grams(1.0),
            potassium = Mass.grams(1.0),
            protein = Mass.grams(1.0),
            riboflavin = Mass.grams(1.0),
            saturatedFat = Mass.grams(1.0),
            selenium = Mass.grams(1.0),
            sodium = Mass.grams(1.0),
            sugar = Mass.grams(1.0),
            thiamin = Mass.grams(1.0),
            totalCarbohydrate = Mass.grams(1.0),
            totalFat = Mass.grams(1.0),
            transFat = Mass.grams(1.0),
            unsaturatedFat = Mass.grams(1.0),
            vitaminA = Mass.grams(1.0),
            vitaminB12 = Mass.grams(1.0),
            vitaminB6 = Mass.grams(1.0),
            vitaminC = Mass.grams(1.0),
            vitaminD = Mass.grams(1.0),
            vitaminE = Mass.grams(1.0),
            vitaminK = Mass.grams(1.0),
            zinc = Mass.grams(1.0),
            name = "Slurp Juice",
            mealType = 1
        )

        OvulationTestRecord::class -> OvulationTestRecord(
            time = time,
            zoneOffset = zoneOffset,
            result = 1,
            metadata = metadata
        )

        OxygenSaturationRecord::class -> OxygenSaturationRecord(
            zoneOffset = zoneOffset,
            percentage = percentage,
            metadata = metadata
        )

        PlannedExerciseSessionRecord::class -> PlannedExerciseSessionRecord(
            startTime = startTime,
            startZoneOffset = startZoneOffset,
            endTime = endTime,
            endZoneOffset = endZoneOffset,
            metadata = metadata,
            blocks = listOf(
                PlannedExerciseBlock(
                    repetitions = 1,
                    steps = listOf(
                        PlannedExerciseStep(
                            exerciseType = 0,
                            exercisePhase = 0,
                            completionGoal = ExerciseCompletionGoal.DistanceAndDurationGoal(
                                distance = distance,
                                duration = duration
                            ),
                            performanceTargets = listOf(
                                ExercisePerformanceTarget.RateOfPerceivedExertionTarget(
                                    1
                                )
                            ),
                            description = "description"
                        )
                    description = "description"
                )
            ),
            exerciseType = 0,
            title = "title",
            notes = "notes"
        )

        PowerRecord::class -> PowerRecord(
            startTime = startTime,
            startZoneOffset = startZoneOffset,
            endTime = endTime,
            endZoneOffset = endZoneOffset,
            samples = listOf(PowerRecord.Sample(time = time, power = power)),
            metadata = metadata
        )

        RespiratoryRateRecord::class -> RespiratoryRateRecord(
            time = time, zoneOffset = zoneOffset, rate = 10.0, metadata = metadata
        )

        RestingHeartRateRecord::class -> RestingHeartRateRecord(
            time = time,
            zoneOffset = zoneOffset,
            beatsPerMinute = 67,
            metadata = metadata
        )

        SexualActivityRecord::class -> SexualActivityRecord(
            metadata = metadata,
            protectionUsed = 1
        )

        SkinTemperatureRecord::class -> SkinTemperatureRecord(
            startTime = startTime,
            startZoneOffset = startZoneOffset,
            endTime = endTime,
            endZoneOffset = endZoneOffset,
            metadata = metadata,
            deltas = listOf(SkinTemperatureRecord.Delta(time = time, delta = delta)),
            baseline = baseline,
            measurementLocation = 1
        )

        SleepSessionRecord::class -> SleepSessionRecord(
            startTime = startTime,
            startZoneOffset = startZoneOffset,
            endTime = endTime,
            endZoneOffset = endZoneOffset,
            metadata = metadata,
            title = "title",
            notes = "notes",
            stages = listOf(
                SleepSessionRecord.Stage(
                    stage = 1
                )
            )
        )

        SpeedRecord::class -> SpeedRecord(
            startTime = startTime,
            startZoneOffset = startZoneOffset,
            endTime = endTime,
            endZoneOffset = endZoneOffset,
            samples = listOf(SpeedRecord.Sample(time = time, speed = speed)),
            metadata = metadata
        )

        StepsCadenceRecord::class -> StepsCadenceRecord(
            startTime = startTime,
            startZoneOffset = startZoneOffset,
            endTime = endTime,
            endZoneOffset = endZoneOffset,
            samples = listOf(StepsCadenceRecord.Sample(time = time, rate = 10.0)),
            metadata = metadata
        )

        StepsRecord::class -> StepsRecord(
            startTime = startTime,
            startZoneOffset = startZoneOffset,
            endTime = endTime,
            endZoneOffset = endZoneOffset,
            count = 100,
            metadata = metadata,
        )

        TotalCaloriesBurnedRecord::class -> TotalCaloriesBurnedRecord(
            startTime = startTime,
            startZoneOffset = startZoneOffset,
            endTime = endTime,
            endZoneOffset = endZoneOffset,
            energy = energy,
            metadata = metadata
        )

        Vo2MaxRecord::class -> Vo2MaxRecord(
            time = time,
            zoneOffset = zoneOffset,
            metadata = metadata,
            vo2MillilitersPerMinuteKilogram = 10.0,
            measurementMethod = 1
        )

        WeightRecord::class -> WeightRecord(
            time = time,
            zoneOffset = zoneOffset,
            weight = weight,
            metadata = metadata
        )

        WheelchairPushesRecord::class -> WheelchairPushesRecord(
            startTime = startTime,
            startZoneOffset = startZoneOffset,
            endTime = endTime,
            endZoneOffset = endZoneOffset,
            count = 69,
            metadata = metadata
        )

        else -> throw IllegalArgumentException("Unsupported record type: $kClass")
    }
}
