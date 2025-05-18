package com.rafapps.taskerhealthconnect

import androidx.annotation.VisibleForTesting
import androidx.health.connect.client.records.ExerciseCompletionGoal
import androidx.health.connect.client.records.ExercisePerformanceTarget
import androidx.health.connect.client.records.ExerciseRouteResult
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.databind.type.TypeFactory
import com.fasterxml.jackson.datatype.jsr310.JavaTimeFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.contains

// https://github.com/FasterXML/jackson-modules-java8/tree/master/datetime
@VisibleForTesting
val objectMapper: ObjectMapper by lazy {
    ObjectMapper()
        .enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .enable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS)
        .disable(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS)
        .disable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS)
        .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
        .disable(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS)
        .disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
        .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
        .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_VALUES)
        .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
        .enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)
        .registerModule(
            JavaTimeModule()
                .enable(JavaTimeFeature.ALWAYS_ALLOW_STRINGIFIED_DATE_TIMESTAMPS)
        )
        .registerModule(
            KotlinModule.Builder()
                .enable(KotlinFeature.NullToEmptyCollection)
                .enable(KotlinFeature.NullToEmptyMap)
                .enable(KotlinFeature.NullIsSameAsDefault)
                .enable(KotlinFeature.UseJavaDurationConversion)
                .build()
        ).registerModule(
            SimpleModule()
                .addDeserializer(ExerciseRouteResult::class.java, ExerciseRouteResultDeserializer())
                .addDeserializer(ExerciseCompletionGoal::class.java, ExerciseCompletionGoalDeserializer())
                .addDeserializer(ExercisePerformanceTarget::class.java, ExercisePerformanceTargetDeserializer())
        )
}


private class ExerciseRouteResultDeserializer : JsonDeserializer<ExerciseRouteResult>() {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): ExerciseRouteResult {
        val codec = p.codec as ObjectMapper
        val node = codec.readTree<JsonNode>(p)

        return when {
            node.has("exerciseRoute") ->
                codec.treeToValue(node, ExerciseRouteResult.Data::class.java)

            else ->
                codec.treeToValue(node, ExerciseRouteResult.NoData::class.java)
        }
    }
}

private class ExerciseCompletionGoalDeserializer : JsonDeserializer<ExerciseCompletionGoal>() {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): ExerciseCompletionGoal {
        val codec = p.codec as ObjectMapper
        val node = codec.readTree<JsonNode>(p)

        return when {
            node.has("distance") && node.has("duration") ->
                codec.treeToValue(node, ExerciseCompletionGoal.DistanceAndDurationGoal::class.java)

            node.has("distance") ->
                codec.treeToValue(node, ExerciseCompletionGoal.DistanceGoal::class.java)

            node.has("duration") ->
                codec.treeToValue(node, ExerciseCompletionGoal.DurationGoal::class.java)

            node.has("steps") ->
                codec.treeToValue(node, ExerciseCompletionGoal.StepsGoal::class.java)

            node.has("repetitions") ->
                codec.treeToValue(node, ExerciseCompletionGoal.RepetitionsGoal::class.java)

            node.has("totalCalories") ->
                codec.treeToValue(node, ExerciseCompletionGoal.TotalCaloriesBurnedGoal::class.java)

            node.has("activeCalories") ->
                codec.treeToValue(node, ExerciseCompletionGoal.ActiveCaloriesBurnedGoal::class.java)

            else ->
                ExerciseCompletionGoal.UnknownGoal
        }
    }
}

private class ExercisePerformanceTargetDeserializer :
    JsonDeserializer<ExercisePerformanceTarget>() {
    override fun deserialize(
        p: JsonParser,
        ctxt: DeserializationContext
    ): ExercisePerformanceTarget {
        val codec = p.codec as ObjectMapper
        val node = codec.readTree<JsonNode>(p)

        return when {
            node.has("minPower") && node.has("maxPower") ->
                codec.treeToValue(node, ExercisePerformanceTarget.PowerTarget::class.java)

            node.has("minSpeed") && node.has("maxSpeed") ->
                codec.treeToValue(node, ExercisePerformanceTarget.SpeedTarget::class.java)

            node.has("minHeartRate") && node.has("maxHeartRate") ->
                codec.treeToValue(node, ExercisePerformanceTarget.HeartRateTarget::class.java)

            node.has("mass") ->
                codec.treeToValue(node, ExercisePerformanceTarget.WeightTarget::class.java)

            node.has("rpe") ->
                codec.treeToValue(
                    node,
                    ExercisePerformanceTarget.RateOfPerceivedExertionTarget::class.java
                )

            else ->
                ExercisePerformanceTarget.UnknownTarget
        }
    }
}

class Serializer(private val mapper: ObjectMapper = objectMapper) {

    fun toString(obj: Any): String = mapper.writeValueAsString(obj)

    fun <T> toObjectList(
        json: String,
        clazz: Class<T>,
        fieldsToAdd: List<String> = emptyList()
    ): List<T> {
        val jsonNode: JsonNode = mapper.readTree(json)
        fieldsToAdd.forEach { field ->
            if (jsonNode.isObject) addMissingField(jsonNode, field)
            else jsonNode.forEach { node -> addMissingField(node, field) }
        }
        val type = TypeFactory.defaultInstance().constructCollectionType(List::class.java, clazz)
        val record: List<T> = mapper.treeToValue(jsonNode, type)
        return record
    }

    private fun addMissingField(node: JsonNode, field: String) {
        if (node.isObject && !node.contains(field)) {
            (node as ObjectNode).replace(field, mapper.createObjectNode())
        }
    }
}
