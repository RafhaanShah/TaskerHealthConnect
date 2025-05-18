package com.rafapps.taskerhealthconnect.read

import android.util.Log
import androidx.health.connect.client.records.PlannedExerciseSessionRecord
import androidx.health.connect.client.records.Record
import androidx.health.connect.client.testing.FakeHealthConnectClient
import com.joaomgcd.taskerpluginlibrary.input.TaskerInput
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResultSucess
import com.rafapps.taskerhealthconnect.FakeClock
import com.rafapps.taskerhealthconnect.FakeContext
import com.rafapps.taskerhealthconnect.HealthConnectRepository
import com.rafapps.taskerhealthconnect.Serializer
import com.rafapps.taskerhealthconnect.TestMapper
import com.rafapps.taskerhealthconnect.readTestFile
import com.rafapps.taskerhealthconnect.recordTypes
import com.rafapps.taskerhealthconnect.testData
import com.rafapps.taskerhealthconnect.time
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
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
    private val expectedList: List<Record>,
    private val expectedOutput: String
) {

    private val context = FakeContext()
    private val clock = FakeClock(timeMs = time.toEpochMilli())
    private val client = FakeHealthConnectClient(clock = clock)
    private val mapper = TestMapper()
    private val serializer = Serializer()
    private val repository = HealthConnectRepository(context) { client }
    private val runner = ReadDataActionRunner({ repository }, serializer)

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{index}:{0}")
        fun data(): List<Array<Any>> = recordTypes.map { className ->
            arrayOf(
                className.simpleName.toString(),
                0,
                Long.MAX_VALUE,
                className,
                listOf(testData.getValue(className)),
                readTestFile("output", "${className.simpleName}")
            )
        }

        // not supported to insert
        val unsupported = setOf(PlannedExerciseSessionRecord::class)
    }

    @Test
    fun testRun() {
        assumeTrue(!unsupported.contains(recordType))
        Log.i("Input", expectedList.toString())
        require(expectedList.isNotEmpty()) { "No expected records found" }
        runBlocking { client.insertRecords(expectedList) }

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
        Log.i("Output", outputString)
        assertEquals(expectedOutput, outputString)
        mapper.assertRecordObject(TestReadRecordsResponse(expectedList), outputString)
    }
}

class TestReadRecordsResponse(val records: List<Record>)
