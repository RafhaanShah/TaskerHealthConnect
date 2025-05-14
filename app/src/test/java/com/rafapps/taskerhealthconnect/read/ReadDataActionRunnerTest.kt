package com.rafapps.taskerhealthconnect.read

import androidx.health.connect.client.records.PlannedExerciseSessionRecord
import androidx.health.connect.client.records.Record
import androidx.health.connect.client.testing.FakeHealthConnectClient
import com.joaomgcd.taskerpluginlibrary.input.TaskerInput
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResultSucess
import com.rafapps.taskerhealthconnect.FakeContext
import com.rafapps.taskerhealthconnect.HealthConnectRepository
import com.rafapps.taskerhealthconnect.Serializer
import com.rafapps.taskerhealthconnect.TestMapper
import com.rafapps.taskerhealthconnect.endTime
import com.rafapps.taskerhealthconnect.recordTypes
import com.rafapps.taskerhealthconnect.startTime
import com.rafapps.taskerhealthconnect.testData
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Assume.assumeTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import kotlin.reflect.KClass

@RunWith(Parameterized::class)
class ReadDataActionRunnerTest(
    private val testName: String,
    private val startTime: Long,
    private val endTime: Long,
    private val recordType: KClass<out Record>,
    private val expectedList: List<Record>
) {

    private val context = FakeContext()
    private val client = FakeHealthConnectClient()
    private val mapper = TestMapper()
    private val serializer = Serializer()
    private val repository = HealthConnectRepository(context, { client })
    private val runner = ReadDataActionRunner({ repository }, serializer)

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{index}:{0}")
        fun data(): List<Array<Any>> {
            if (recordTypes.size != testData.size) error("TestData is missing records")

            return recordTypes.map { className ->
                arrayOf(
                    className.simpleName.toString(),
                    startTime.toEpochMilli(),
                    endTime.toEpochMilli(),
                    className,
                    listOf(testData.getValue(className))
                )
            }
        }

        // not supported to insert
        val unsupported = setOf(PlannedExerciseSessionRecord::class)
    }

    @Test
    fun testRun() {
        assumeTrue(!unsupported.contains(recordType))

        val expectedRecords = listOf(testData.getValue(recordType))
        runBlocking { client.insertRecords(expectedRecords) }

        val output = runner.run(
            context,
            TaskerInput(
                ReadDataInput(
                    recordType.simpleName.toString(),
                    startTime.toString(),
                    endTime.toString()
                )
            )
        )
        assertTrue(output.success)
        assertTrue(output is TaskerPluginResultSucess)
        val outputString: String =
            (output as TaskerPluginResultSucess).regular?.healthConnectResult.toString()

        // assert all inserted values were read as expected
        mapper.assertRecordObject(TestReadRecordsResponse(expectedRecords), outputString)
    }
}

class TestReadRecordsResponse(val records: List<Record>)
