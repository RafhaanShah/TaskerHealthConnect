package com.rafapps.taskerhealthconnect.healthdata

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
import com.rafapps.taskerhealthconnect.databinding.ActivityHealthDataBinding
import kotlinx.coroutines.launch
import java.time.Instant

class HealthDataActivity : AppCompatActivity(),
    TaskerPluginConfig<HealthDataInput> {

    private val TAG = "HealthDataActivity"
    private lateinit var binding: ActivityHealthDataBinding
    private val repository by lazy { HealthConnectRepository(this) }
    private val taskerHelper by lazy { HealthDataActionHelper(this) }

    private val permissionsLauncher =
        registerForActivityResult(
            PermissionController.createRequestPermissionResultContract()
        ) { granted ->
            if (granted.containsAll(repository.permissions))
                onPermissionGranted()
        }

    override val context: Context
        get() = this

    override val inputForTasker: TaskerInput<HealthDataInput>
        get() = TaskerInput(
            HealthDataInput(recordType = getInputRecordType(), fromTimeMillis = getInputFromTime())
        )

    override fun assignFromInput(input: TaskerInput<HealthDataInput>) {
        binding.recordTypeText.editText?.setText(input.regular.recordType)
        binding.fromTimeText.editText?.setText(input.regular.fromTimeMillis)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityHealthDataBinding.inflate(layoutInflater)
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

    private fun getInputRecordType(): String {
        return binding.recordTypeText.editText?.text.toString()
    }

    private fun getInputFromTime(): String {
        return binding.fromTimeText.editText?.text.toString()
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
                runCatching {
                    val recordType = getInputRecordType()
                    val startTime = Instant.ofEpochMilli(getInputFromTime().toLong())
                    val output = repository.getData(recordType, startTime)
                    Log.d(TAG, output.toString(2))
                }.onFailure { err ->
                    Log.e(TAG, "Repository error:", err)
                }
            }
        }
    }
}
