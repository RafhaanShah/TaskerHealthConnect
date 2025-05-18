package com.rafapps.taskerhealthconnect.write

import android.util.Log
import androidx.health.connect.client.records.PlannedExerciseSessionRecord
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
import com.rafapps.taskerhealthconnect.readTestFile
import com.rafapps.taskerhealthconnect.recordTypes
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Assume.assumeTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
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
    private val repository = HealthConnectRepository(context, { client })
    private val runner = WriteDataActionRunner({ repository }, serializer)

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{index}:{0}")
        fun data(): List<Array<Any>> = recordTypes.map { className ->
            val name = className.simpleName.toString()
            arrayOf(name, readTestFile("input", name))
        }

        // not supported to insert
        val unsupported = setOf(PlannedExerciseSessionRecord::class.simpleName.toString())
    }

    @Test
    @Suppress("UNCHECKED_CAST")
    fun testRun() {
        assumeTrue(!unsupported.contains(recordType))
        Log.i("Input", inputString)

        val output = runner.run(context, TaskerInput(WriteDataInput(recordType, inputString)))
        assertTrue(output.success)
        assertTrue(output is TaskerPluginResultSucess)

        val outputString: String =
            (output as TaskerPluginResultSucess).regular?.healthConnectResult.toString()
        Log.i("Output", outputString)

        val clazz = Class.forName("$healthConnectRecordsPackage.$recordType")
        val expectedRecords = runBlocking {
            client.readRecords(
                ReadRecordsRequest(
                    (clazz as Class<Record>).kotlin,
                    TimeRangeFilter.after(Instant.ofEpochMilli(0))
                )
            ).records
        }

        require(expectedRecords.isNotEmpty()) { "No expected records found"}
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
