package com.rafapps.taskerhealthconnect

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.aggregate.AggregateMetric
import androidx.health.connect.client.aggregate.AggregationResult
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.*
import androidx.health.connect.client.request.AggregateGroupByDurationRequest
import androidx.health.connect.client.time.TimeRangeFilter
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception
import java.time.Duration
import java.time.Instant
import kotlin.reflect.KClass
import kotlin.reflect.full.companionObject
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible
import kotlin.reflect.typeOf

class HealthConnectRepository(private val context: Context) {

    companion object {
        val TAG = "HealthConnectRepository"
    }

    private val playStoreUri =
        "https://play.google.com/store/apps/details?id=com.google.android.apps.healthdata"

    private val client by lazy { HealthConnectClient.getOrCreate(context) }

    val recordTypes = setOf<KClass<out Record>>(
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
        LeanBodyMassRecord::class,
        MenstruationFlowRecord::class,
        NutritionRecord::class,
        OvulationTestRecord::class,
        OxygenSaturationRecord::class,
        PowerRecord::class,
        RespiratoryRateRecord::class,
        RestingHeartRateRecord::class,
        SexualActivityRecord::class,
        SleepSessionRecord::class,
        SpeedRecord::class,
        StepsCadenceRecord::class,
        StepsRecord::class,
        TotalCaloriesBurnedRecord::class,
        Vo2MaxRecord::class,
        WeightRecord::class,
        WheelchairPushesRecord::class,
    )

    val permissions by lazy {  recordTypes.map { HealthPermission.createReadPermission(it) }.toSet() }

    fun getAllAggregateMetrics(): Set<AggregateMetric<*>> {
        val aggregateMetrics = mutableSetOf<AggregateMetric<*>>()
        val targetType = typeOf<AggregateMetric<*>>()

        recordTypes.forEach { recordType ->
            try {
                val companionObj = recordType.companionObject?.objectInstance ?: return@forEach
                val properties = companionObj::class.declaredMemberProperties.filter {
                    it.returnType.classifier == targetType.classifier
                }

                properties.forEach { property ->
                    val value = property.getter.call(companionObj) as AggregateMetric<*>
                    aggregateMetrics.add(value)
                }
            }
            catch (e: Exception) {
                Log.e(TAG, "getAllAggregateMetrics: ", e)
            }
        }
        return aggregateMetrics.toSet()
    }

    fun installHealthConnect() {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(playStoreUri)
            setPackage("com.android.vending")
        }

        runCatching { context.startActivity(intent) }
    }

    fun isAvailable(): Boolean = HealthConnectClient.isAvailable(context)

    suspend fun hasPermissions(): Boolean {
        val granted = client.permissionController.getGrantedPermissions(permissions)
        return granted.containsAll(permissions)
    }

    suspend fun getData(
        startTime: Instant,
        endTime: Instant
    ): String {
        val result: JSONArray = JSONArray()
        val metrics = getAllAggregateMetrics()

        try {
            val response = client.aggregateGroupByDuration(
                AggregateGroupByDurationRequest(
                    metrics = metrics,
                    timeRangeFilter = TimeRangeFilter.between(startTime, endTime),
                    timeRangeSlicer = Duration.ofDays(1)
                )
            )
            response.forEach {
                val data = mutableMapOf<String, Any?>()
                val properties = AggregationResult::class.memberProperties
                properties.forEach { p ->
                    run {
                        p.isAccessible = true;
                        if (p.name == "doubleValues" || p.name == "longValues") {
                            val values = p.call(it.result)

                            data.putAll(values as Map<out String, Any?>)
                        }
                    }
                }
                result.put(JSONObject(mapOf(
                    "startTime" to it.startTime,
                    "endTime" to it.endTime,
                    "result" to data
                )))
            }
            return result.toString()
        }
        catch (e: Exception) {
            Log.e(TAG, "getData: ", e)
            return "[]"
        }

    }
}
