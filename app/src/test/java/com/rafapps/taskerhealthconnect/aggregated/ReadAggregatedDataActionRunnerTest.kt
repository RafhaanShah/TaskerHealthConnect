package com.rafapps.taskerhealthconnect.aggregated

import android.util.Log
import androidx.health.connect.client.aggregate.AggregateMetric
import androidx.health.connect.client.testing.AggregationResult
import androidx.health.connect.client.testing.FakeHealthConnectClient
import androidx.health.connect.client.testing.stubs.stub
import com.joaomgcd.taskerpluginlibrary.input.TaskerInput
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResultSucess
import com.rafapps.taskerhealthconnect.FakeContext
import com.rafapps.taskerhealthconnect.HealthConnectRepository
import com.rafapps.taskerhealthconnect.Serializer
import com.rafapps.taskerhealthconnect.TestMapper
import com.rafapps.taskerhealthconnect.aggregateMetrics
import com.rafapps.taskerhealthconnect.endTime
import com.rafapps.taskerhealthconnect.startTime
import org.junit.Assert.assertTrue
import org.junit.Assume.assumeTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class ReadAggregatedDataActionRunnerTest(
    private val metricKey: String,
    private val metric: AggregateMetric<*>,
    private val testData: Any,
    private val startTime: Long,
    private val endTime: Long
) {

    private val context = FakeContext()
    private val client = FakeHealthConnectClient()
    private val mapper = TestMapper()
    private val serializer = Serializer()
    private val repository = HealthConnectRepository(context) { client }
    private val runner = ReadAggregatedDataActionRunner({ repository }, serializer)

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{index}:{0}")
        fun data(): List<Array<Any>> {
            return aggregateMetrics.map { data ->
                arrayOf(
                    data.key,
                    data.metric,
                    data.testData,
                    startTime.toEpochMilli(),
                    endTime.toEpochMilli(),
                )
            }.also {
                check(it.isNotEmpty()) { "No aggregate metrics found" }
            }
        }
    }

    @Test
    fun testRun() {
        val aggregateResult = AggregationResult(metrics = mapOf(metric to testData))
        assumeTrue(aggregateResult.doubleValues.isNotEmpty() or aggregateResult.longValues.isNotEmpty())
        client.overrides.aggregate = stub(aggregateResult)

        val output = runner.run(
            context, TaskerInput(
                ReadAggregatedDataInput(
                    metricKey, startTime.toString(), endTime.toString()
                )
            )
        )
        assertTrue(output.success)
        assertTrue(output is TaskerPluginResultSucess)
        val outputString: String =
            (output as TaskerPluginResultSucess).regular?.healthConnectResult.toString()

        Log.i("TEST", outputString)
        mapper.assertRecordObject(aggregateResult, outputString)
    }
}
