package com.rafapps.taskerhealthconnect.delete

import android.util.Log
import androidx.health.connect.client.records.Record
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.testing.FakeHealthConnectClient
import androidx.health.connect.client.time.TimeRangeFilter
import com.joaomgcd.taskerpluginlibrary.input.TaskerInput
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResultSucess
import com.rafapps.taskerhealthconnect.FakeClock
import com.rafapps.taskerhealthconnect.FakeContext
import com.rafapps.taskerhealthconnect.HealthConnectRepository
import com.rafapps.taskerhealthconnect.recordTypes
import com.rafapps.taskerhealthconnect.testRecords
import com.rafapps.taskerhealthconnect.time
import com.rafapps.taskerhealthconnect.unsupportedRecordTypes
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assume.assumeTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.time.Duration
import kotlin.reflect.KClass

@RunWith(Parameterized::class)
class DeleteDataActionRunnerTest(
    private val testName: String,
    private val recordType: KClass<out Record>
) {

    private val context = FakeContext()
    private val clock = FakeClock(timeMs = time.toEpochMilli())
    private val client = FakeHealthConnectClient(clock = clock)
    private val repository = HealthConnectRepository(context) { client }
    private val runner = DeleteDataActionRunner { repository }

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{index}:{0}")
        fun data(): List<Array<Any>> = recordTypes.map { className ->
            arrayOf(
                className.simpleName.toString(),
                className
            )
        }
    }

    @Test
    fun testRun() {
        assumeTrue(!unsupportedRecordTypes.contains(recordType))
        Log.i("Input", "$recordType")
        val record = testRecords.getValue(recordType)
        
        runBlocking {
            client.insertRecords(listOf(record))

            val initialResponse = client.readRecords(
                ReadRecordsRequest(
                    recordType = recordType,
                    timeRangeFilter = TimeRangeFilter.between(time.minusSeconds(1), time.plus(Duration.ofDays(1)))
                )
            )
            assertEquals("Record should be inserted before deletion", 1, initialResponse.records.size)

            val output = runner.run(
                context,
                TaskerInput(
                    DeleteDataInput(
                        recordType = recordType.simpleName.toString(),
                        startTime = "0",
                        endTime = Long.MAX_VALUE.toString()
                    )
                )
            )

            assertTrue(output.success)
            assertTrue(output is TaskerPluginResultSucess)
            
            val finalResponse = client.readRecords(
                ReadRecordsRequest(
                    recordType = recordType,
                    timeRangeFilter = TimeRangeFilter.between(time.minusSeconds(1), time.plus(Duration.ofDays(1)))
                )
            )
            assertEquals("Record should be deleted", 0, finalResponse.records.size)
        }
    }
}
