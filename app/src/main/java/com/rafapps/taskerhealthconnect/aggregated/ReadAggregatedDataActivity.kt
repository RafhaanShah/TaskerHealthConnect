package com.rafapps.taskerhealthconnect.aggregated

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.isVisible
import androidx.health.connect.client.PermissionController
import androidx.lifecycle.lifecycleScope
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfig
import com.joaomgcd.taskerpluginlibrary.input.TaskerInput
import com.rafapps.taskerhealthconnect.BuildConfig
import com.rafapps.taskerhealthconnect.HealthConnectRepository
import com.rafapps.taskerhealthconnect.R
import com.rafapps.taskerhealthconnect.databinding.ActivityAggregatedDataBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.Instant

class ReadAggregatedDataActivity : AppCompatActivity(),
    TaskerPluginConfig<ReadAggregatedDataInput> {

    private val TAG = "AggregatedHealthDataActivity"
    private lateinit var binding: ActivityAggregatedDataBinding
    private val repository by lazy { HealthConnectRepository(this) }
    private val taskerHelper by lazy { ReadAggregatedDataHelper(this) }

    private val permissionsLauncher =
        registerForActivityResult(
            PermissionController.createRequestPermissionResultContract()
        ) { granted ->
            if (granted.containsAll(repository.permissions))
                onPermissionGranted()
        }

    override val context: Context
        get() = this

    override val inputForTasker: TaskerInput<ReadAggregatedDataInput>
        get() = TaskerInput(
            ReadAggregatedDataInput(
                aggregateMetric = getInputAggregateMetric(),
                startTime = getInputStartTime(),
                endTime = getInputEndTime()
            )
        )

    override fun assignFromInput(input: TaskerInput<ReadAggregatedDataInput>) {
        binding.aggregateMetricText.editText?.setText(input.regular.aggregateMetric)
        binding.startTimeText.editText?.setText(input.regular.startTime)
        binding.endTimeText.editText?.setText(input.regular.endTime)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityAggregatedDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setDebugButton()
        taskerHelper.onCreate()
    }

    override fun onResume() {
        super.onResume()
        setButtonState()
    }

    private fun setButtonState() {
        lifecycleScope.launch {
            when {
                !repository.isAvailable() -> {
                    binding.button.text = getString(R.string.install)
                    binding.button.setOnClickListener { repository.installHealthConnect() }
                }

                !repository.hasPermissions() -> {
                    binding.button.text = getString(R.string.grant_permissions)
                    binding.button.setOnClickListener {
                        permissionsLauncher.launch(repository.permissions)
                    }
                }

                else -> onPermissionGranted()
            }
        }
    }

    private fun onPermissionGranted() {
        Log.d(TAG, "onPermissionGranted")
        binding.button.text = getString(R.string.done)
        binding.button.setOnClickListener {
            hideKeyboard()
            taskerHelper.finishForTasker()
        }
    }

    private fun getInputAggregateMetric(): String = binding.aggregateMetricText.editText?.text.toString()

    private fun getInputStartTime(): String = binding.startTimeText.editText?.text.toString()

    private fun getInputEndTime(): String = binding.endTimeText.editText?.text.toString()

    private fun hideKeyboard() {
        (getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager)?.hideSoftInputFromWindow(
            binding.root.windowToken,
            0
        )
    }

    private fun setDebugButton() {
        binding.debugButton.isVisible = BuildConfig.DEBUG
        binding.debugButton.setOnClickListener {
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    runCatching {
                        val aggregateMetric = getInputAggregateMetric()
                        val startTime = Instant.ofEpochMilli(getInputStartTime().toLong())
                        val endTime = Instant.ofEpochMilli(getInputEndTime().toLong())
                        val output = repository.readAggregatedData(aggregateMetric, startTime, endTime)
                        Log.d(TAG, output)
                    }.onFailure { err ->
                        Log.e(TAG, "Repository error:", err)
                    }
                }
            }
        }
    }
}
