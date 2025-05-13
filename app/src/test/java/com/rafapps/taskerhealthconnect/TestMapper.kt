package com.rafapps.taskerhealthconnect

import androidx.health.connect.client.records.ExerciseRouteResult
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
import com.fasterxml.jackson.core.type.TypeReference
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import java.time.Duration
import java.time.Instant
import java.time.ZoneOffset
import kotlin.reflect.full.memberProperties

@Suppress("UNCHECKED_CAST")
class TestMapper {
    private val mapper = objectMapper

    fun assertRecordList(expectedList: List<Any>, input: String) =
        assertRecordList(expectedList, stringToList(input))

    private fun assertRecordList(expectedList: List<Any>, input: List<Map<String, Any>>) {
        input.forEachIndexed { index, map ->
            assertRecord(objToMap(expectedList[index]), map)
        }
    }

    private fun assertRecord(expectedMap: Map<String, Any?>, actualMap: Map<String, Any>) {
        actualMap.forEach { (key, value) ->
            val expected = expectedMap[key]
            when (key) {
                in listKeys -> assertRecordList(
                    expected as List<Map<String, Any?>>,
                    value as List<Map<String, Any>>
                )
                in objectKeys -> assertRecord(
                    objToMap(expected),
                    value as Map<String, Any>
                )

                in energyKeys -> assertEnergy(expected as Energy, value)
                in instantKeys -> assertEquals((expected as Instant).toEpochMilli(), value)
                in lengthKeys -> assertLength((expected as Length), value)
                in massKeys -> assertMass(expected as Mass, value)
                in powerKeys -> assertPower(expected as Power, value)
                in pressureKeys -> assertPressure(expected as Pressure, value)
                in temperatureKeys -> assertTemperature(expected as Temperature, value)
                in velocityKeys -> assertVelocity(expected as Velocity, value)
                in zoneOffsetKeys -> assertEquals((expected as ZoneOffset).id, value)

                "delta" -> assertTemperatureDelta((expected as TemperatureDelta), value)
                "duration" -> assertEquals((expected as Duration).toMillis(), value)
                "level" -> assertBloodGlucose(expected as BloodGlucose, value)
                "percentage" -> assertPercentage(expected as Percentage, value)
                "volume" -> assertVolume(expected as Volume, value)
                else -> {
                    when (expected) {
                        is Int -> assertEquals(expected, (value as Number).toInt())
                        is Long -> assertEquals(expected, (value as Number).toLong())
                        else -> assertEquals(expected, value)
                    }
                }
            }
        }
    }

    private fun assertEnergy(expected: Energy, actualAny: Any) {
        val actual = actualAny as Map<String, Any>
        val type = actual.getValue("type").toString().uppercase()
        val value = actual.getValue("value")
        when (type) {
            "CALORIES" -> assertEquals(expected.inCalories, value)
            "KILOCALORIES" -> assertEquals(expected.inKilocalories, value)
            "JOULES" -> assertEquals(expected.inJoules, value)
            "KILOJOULES" -> assertEquals(expected.inKilojoules, value)
            else -> fail("Invalid input: $actual")
        }
    }

    private fun assertTemperature(expected: Temperature, actualAny: Any) {
        val actual = actualAny as Map<String, Any>
        val type = actual.getValue("type").toString().uppercase()
        val value = actual.getValue("value")
        when (type) {
            "CELSIUS" -> assertEquals(expected.inCelsius, value)
            "FAHRENHEIT" -> assertEquals(expected.inFahrenheit, value)
            else -> fail("Invalid input: $actual")
        }
    }

    private fun assertTemperatureDelta(expected: TemperatureDelta, actualAny: Any) {
        val actual = actualAny as Map<String, Any>
        val type = actual.getValue("temperatureUnit").toString().uppercase()
        val value = actual.getValue("value")
        when (type) {
            "CELSIUS" -> assertEquals(expected.inCelsius, value)
            "FAHRENHEIT" -> assertEquals(expected.inFahrenheit, value)
            else -> fail("Invalid input: $actual")
        }
    }

    private fun assertPower(expected: Power, actualAny: Any) {
        val actual = actualAny as Map<String, Any>
        val type = actual.getValue("type").toString().uppercase()
        val value = actual.getValue("value")
        when (type) {
            "WATTS" -> assertEquals(expected.inWatts, value)
            "KILOCALORIES_PER_DAY" -> assertEquals(expected.inKilocaloriesPerDay, value)
            else -> fail("Invalid input: $actual")
        }
    }

    private fun assertBloodGlucose(expected: BloodGlucose, actualAny: Any) {
        val actual = actualAny as Map<String, Any>
        val type = actual.getValue("type").toString().uppercase()
        val value = actual.getValue("value")
        when (type) {
            "MILLIMOLES_PER_LITER" -> assertEquals(expected.inMillimolesPerLiter, value)
            "MILLIGRAMS_PER_DECILITER" -> assertEquals(expected.inMilligramsPerDeciliter, value)
            else -> fail("Invalid input: $actual")
        }
    }

    private fun assertPressure(expected: Pressure, actualAny: Any) {
        val actual = actualAny as Map<String, Any>
        val value = actual.getValue("value")
        assertEquals(expected.inMillimetersOfMercury, value)
    }

    private fun assertPercentage(expected: Percentage, actualAny: Any) {
        val actual = actualAny as Map<String, Any>
        val value = actual.getValue("value")
        assertEquals(expected.value, value)
    }

    private fun assertMass(expected: Mass, actualAny: Any) {
        val actual = actualAny as Map<String, Any>
        val type = actual.getValue("type").toString().uppercase()
        val value = actual.getValue("value")
        when (type) {
            "GRAMS" -> assertEquals(expected.inGrams, value)
            "KILOGRAMS" -> assertEquals(expected.inKilograms, value)
            "MILLIGRAMS" -> assertEquals(expected.inMilligrams, value)
            "MICROGRAMS" -> assertEquals(expected.inMicrograms, value)
            "OUNCES" -> assertEquals(expected.inOunces, value)
            "POUNDS" -> assertEquals(expected.inPounds, value)
            else -> fail("Invalid input: $actual")
        }
    }

    private fun assertLength(expected: Length, actualAny: Any) {
        val actual = actualAny as Map<String, Any>
        val type = actual.getValue("type").toString().uppercase()
        val value = actual.getValue("value")
        when (type) {
            "METERS" -> assertEquals(expected.inMeters, value)
            "KILOMETERS" -> assertEquals(expected.inKilometers, value)
            "MILES" -> assertEquals(expected.inMiles, value)
            "INCHES" -> assertEquals(expected.inInches, value)
            "FEET" -> assertEquals(expected.inFeet, value)
            else -> fail("Invalid input: $actual")
        }
    }

    private fun assertExerciseRouteResult(expected: ExerciseRouteResult, actualAny: Any) {
        val actual = actualAny as Map<String, Any>

        when (expected) {
            is ExerciseRouteResult.Data -> {
                assertRecord(
                    objToMap(expected.exerciseRoute),
                    actual.getValue("exerciseRoute") as Map<String, Any>
                )
            }

            else -> {}
        }
    }

    private fun assertVolume(expected: Volume, actualAny: Any) {
        val actual = actualAny as Map<String, Any>
        val type = actual.getValue("type").toString().uppercase()
        val value = actual.getValue("value")
        when (type) {
            "LITERS" -> assertEquals(expected.inLiters, value)
            "MILLILITERS" -> assertEquals(expected.inMilliliters, value)
            "FLUID_OUNCES_US" -> assertEquals(expected.inFluidOuncesUs, value)
            else -> fail("Invalid input: $actual")
        }
    }

    private fun assertVelocity(expected: Velocity, actualAny: Any) {
        val actual = actualAny as Map<String, Any>
        val type = actual.getValue("type").toString().uppercase()
        val value = actual.getValue("value")
        when (type) {
            "METERS_PER_SECOND" -> assertEquals(expected.inMetersPerSecond, value)
            "KILOMETERS_PER_HOUR" -> assertEquals(expected.inKilometersPerHour, value)
            "MILES_PER_HOUR" -> assertEquals(expected.inMilesPerHour, value)
            else -> fail("Invalid input: $actual")
        }
    }

    fun stringToList(input: String): List<Map<String, Any>> {
        val node = mapper.readTree(input)
        return when {
            node.isArray -> mapper.readValue(
                input,
                object : TypeReference<List<Map<String, Any>>>() {})

            node.isObject -> listOf(
                mapper.readValue(
                    input,
                    object : TypeReference<Map<String, Any>>() {})
            )

            else -> throw IllegalArgumentException("Input is neither a valid JSON object nor an array.")

        }
    }

    private fun objToMap(obj: Any?): Map<String, Any?> {
        if(obj == null) return emptyMap()
        return obj::class.memberProperties.associate { prop ->
            prop.name to prop.getter.call(obj)
        }
    }

    companion object {
        private val massKeys = setOf(
            "biotin",
            "caffeine",
            "calcium",
            "chloride",
            "cholesterol",
            "chromium",
            "copper",
            "dietaryFiber",
            "folate",
            "folicAcid",
            "iodine",
            "iron",
            "magnesium",
            "manganese",
            "mass",
            "molybdenum",
            "monounsaturatedFat",
            "niacin",
            "pantothenicAcid",
            "phosphorus",
            "polyunsaturatedFat",
            "potassium",
            "protein",
            "riboflavin",
            "saturatedFat",
            "selenium",
            "sodium",
            "sugar",
            "thiamin",
            "totalCarbohydrate",
            "totalFat",
            "transFat",
            "unsaturatedFat",
            "vitaminA",
            "vitaminB6",
            "vitaminB12",
            "vitaminC",
            "vitaminD",
            "vitaminE",
            "vitaminK",
            "weight",
            "zinc",
        )

        val lengthKeys = setOf(
            "distance",
            "elevation",
            "horizontalAccuracy",
            "verticalAccuracy",
            "altitude",
            "length",
            "height"
        )

        private val listKeys = setOf(
            "samples",
            "segments",
            "laps",
            "route",
            "blocks",
            "steps",
            "performanceTargets",
            "deltas",
            "stages"
        )

        private val objectKeys = setOf("exerciseRouteResult", "exerciseRoute")
        private val energyKeys = setOf("energy", "totalCalories", "energyFromFat")
        private val instantKeys = setOf("time", "startTime", "endTime")
        private val powerKeys = setOf("power", "minPower", "maxPower", "basalMetabolicRate")
        private val pressureKeys = setOf("systolic", "diastolic")
        private val temperatureKeys = setOf("temperature", "baseline")
        private val velocityKeys = setOf("speed", "minSpeed", "maxSpeed")
        private val zoneOffsetKeys = setOf("startZoneOffset", "endZoneOffset")
    }
}
