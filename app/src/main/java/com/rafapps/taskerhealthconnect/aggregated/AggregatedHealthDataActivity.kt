package com.rafapps.taskerhealthconnect.aggregated

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.isVisible
import androidx.health.connect.client.PermissionController
import androidx.lifecycle.lifecycleScope
import com.google.android.material.internal.ViewUtils.hideKeyboard
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfig
import com.joaomgcd.taskerpluginlibrary.input.TaskerInput
import com.rafapps.taskerhealthconnect.BuildConfig
import com.rafapps.taskerhealthconnect.HealthConnectRepository
import com.rafapps.taskerhealthconnect.R
import com.rafapps.taskerhealthconnect.databinding.ActivityAggregatedHealthDataBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.Instant

class AggregatedHealthDataActivity : AppCompatActivity(),
    TaskerPluginConfig<AggregatedHealthDataInput> {

    private val TAG = "AggregatedHealthDataActivity"
    private lateinit var binding: ActivityAggregatedHealthDataBinding
    private val repository by lazy { HealthConnectRepository(this) }
    private val taskerHelper by lazy { AggregatedHealthDataActionHelper(this) }

    private val permissionsLauncher =
        registerForActivityResult(
            PermissionController.createRequestPermissionResultContract()
        ) { granted ->
            if (granted.containsAll(repository.permissions))
                onPermissionGranted()
        }

    override val context: Context
        get() = this

    override val inputForTasker: TaskerInput<AggregatedHealthDataInput>
        get() = TaskerInput(
            AggregatedHealthDataInput(days = getInputDays())
        )

    override fun assignFromInput(input: TaskerInput<AggregatedHealthDataInput>) {
        binding.daysText.editText?.setText(input.regular.days)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityAggregatedHealthDataBinding.inflate(layoutInflater)
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

    private fun getInputDays(): String {
        return binding.daysText.editText?.text.toString()
    }

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
                        val startTime = AggregatedHealthDataActionRunner
                            .daysToOffsetTime(getInputDays().toLong())
                        val output = repository.getAggregateData(startTime)
                        Log.d(TAG, output.toString())
                    }.onFailure { err ->
                        Log.e(TAG, "Repository error:", err)
                    }
                }
            }
        }
    }
}
