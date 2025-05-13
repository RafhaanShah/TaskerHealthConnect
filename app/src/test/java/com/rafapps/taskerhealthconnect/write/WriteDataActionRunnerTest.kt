package com.rafapps.taskerhealthconnect.write

import androidx.health.connect.client.records.Record
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.testing.FakeHealthConnectClient
import androidx.health.connect.client.time.TimeRangeFilter
import com.joaomgcd.taskerpluginlibrary.input.TaskerInput
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResultSucess
import com.rafapps.taskerhealthconnect.FakeContext
import com.rafapps.taskerhealthconnect.HealthConnectRepository
import com.rafapps.taskerhealthconnect.Serializer
import com.rafapps.taskerhealthconnect.TestMapper
import com.rafapps.taskerhealthconnect.healthConnectRecordsPackage
import com.rafapps.taskerhealthconnect.recordTypes
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Assume.assumeTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.io.File
import java.time.Instant

@RunWith(Parameterized::class)
class WriteDataActionRunnerTest(
    private val recordType: String,
    private val inputString: String
) {

    private val context = FakeContext()
    private val client = FakeHealthConnectClient()
    private val mapper = TestMapper()
    private val serializer = Serializer()
    private val repository = HealthConnectRepository(context, client)
    private val runner = WriteDataActionRunner({ repository }, serializer)

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{index}:{0}")
        fun data(): List<Array<Any>> {
            val inputDir = File("src/test/resources/input")
            val recordTypeSet: Set<String> = recordTypes.map { it.simpleName.toString() }.toSet()

            if (!inputDir.exists() || !inputDir.isDirectory)
                error("Input directory not found: ${inputDir.absolutePath}")

            val res = inputDir
                .listFiles { file -> file.extension == "json" }
                ?.map { file ->
                    val className = file.nameWithoutExtension
                    require(recordTypeSet.contains(className)) {
                        "Unknown record type: $className"
                    }
                    arrayOf<Any>(className, file.readText(Charsets.UTF_8))
                } ?: error("No JSON files found in input directory: ${inputDir.absolutePath}")

            require(recordTypeSet.size == res.size) {
                "Missing record types: ${recordTypeSet - res.map { it[0] }.toSet()}"
            }


            return res
        }

        // not supported to insert
        val unsupported = setOf("PlannedExerciseSessionRecord")
    }

    @Test
    @Suppress("UNCHECKED_CAST")
    fun testRun() {
        assumeTrue(!unsupported.contains(recordType))

        val clazz = Class.forName("$healthConnectRecordsPackage.$recordType")
        val output = runner.run(context, TaskerInput(WriteDataInput(recordType, inputString)))
        assertTrue(output.success)
        assertTrue(output is TaskerPluginResultSucess)

        val outputString: String =
            (output as TaskerPluginResultSucess).regular?.healthConnectResult.toString()

        val expectedRecords = runBlocking {
            client.readRecords(
                ReadRecordsRequest(
                    (clazz as Class<Record>).kotlin,
                    TimeRangeFilter.after(Instant.ofEpochMilli(0))
                )
            ).records
        }

        val outputIds = mapper.stringToList(outputString)
            .first()
            .getValue("recordIdsList")
                as List<String>

        // assert all reported IDs were actually inserted
        outputIds.forEach { outputId ->
            assertTrue(expectedRecords.any { record -> record.metadata.id == outputId })
        }

        // assert all provided values were inserted as expected
        mapper.assertRecordList(expectedRecords, inputString)
    }
}
