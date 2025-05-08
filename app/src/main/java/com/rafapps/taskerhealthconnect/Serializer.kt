package com.rafapps.taskerhealthconnect

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.databind.type.TypeFactory
import com.fasterxml.jackson.datatype.jsr310.JavaTimeFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.contains

// https://github.com/FasterXML/jackson-modules-java8/tree/master/datetime
private val objectMapper: ObjectMapper by lazy {
    ObjectMapper()
        .enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .enable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS)
        .disable(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS)
        .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
        .disable(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS)
        .disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
        .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
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
        )
}

class Serializer(private val mapper: ObjectMapper = objectMapper) {

    fun toString(obj: Any): String = mapper.writeValueAsString(obj)

    fun <T> toObjectList(
        json: String,
        kClass: Class<T>,
        fieldsToAdd: List<String> = emptyList()
    ): List<T> {
        val jsonNode: JsonNode = mapper.readTree(json)
        fieldsToAdd.forEach { field ->
            if (jsonNode.isObject) addMissingField(jsonNode, field)
            else jsonNode.forEach { node -> addMissingField(node, field) }
        }
        val type = TypeFactory.defaultInstance().constructCollectionType(List::class.java, kClass)
        val record: List<T> = mapper.treeToValue(jsonNode, type)
        return record
    }

    private fun addMissingField(node: JsonNode, field: String) {
        if (node.isObject && !node.contains(field)) {
            (node as ObjectNode).replace(field, mapper.createObjectNode())
        }
    }
}
