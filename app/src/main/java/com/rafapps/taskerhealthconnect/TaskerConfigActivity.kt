package com.rafapps.taskerhealthconnect

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.isVisible
import androidx.health.connect.client.PermissionController
import androidx.lifecycle.lifecycleScope
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfig
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfigHelper
import com.joaomgcd.taskerpluginlibrary.input.TaskerInput
import com.rafapps.taskerhealthconnect.databinding.ActivityHealthConnectTaskerBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class TaskerConfigActivity<TInput : Any,
        TConfigHelper : TaskerPluginConfigHelper<*, *, *>> :
    AppCompatActivity(), TaskerPluginConfig<TInput> {

    private lateinit var binding: ActivityHealthConnectTaskerBinding
    protected val repository by lazy { HealthConnectRepository(this) }
    protected abstract val taskerHelper: TConfigHelper
    protected abstract val tag: String
    protected abstract val requiredPermissions: Set<String>

    override val context: Context
        get() = this

    abstract override val inputForTasker: TaskerInput<TInput>

    private val permissionsLauncher =
        registerForActivityResult(
            PermissionController.createRequestPermissionResultContract()
        ) { granted ->
            if (granted.containsAll(requiredPermissions))
                onPermissionGranted()
        }

    abstract override fun assignFromInput(input: TaskerInput<TInput>)

    open suspend fun runDebugAction(): Any = { }

    protected abstract fun provideContentView(layoutInflater: LayoutInflater): View

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(tag, "onCreate")
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        binding = ActivityHealthConnectTaskerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setDebugButton()
        val contentView = provideContentView(layoutInflater)
        binding.contentContainer.addView(contentView)
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

                !repository.hasPermissions(requiredPermissions) -> {
                    binding.button.text = getString(R.string.grant_permissions)
                    binding.button.setOnClickListener {
                        permissionsLauncher.launch(requiredPermissions)
                    }
                }

                else -> onPermissionGranted()
            }
        }
    }

    private fun onPermissionGranted() {
        Log.d(tag, "onPermissionGranted")
        binding.button.text = getString(R.string.done)
        binding.button.setOnClickListener {
            hideKeyboard()
            taskerHelper.finishForTasker()
        }
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
                        val output = runDebugAction()
                        Log.d(tag, output.toString())
                    }.onFailure { err ->
                        Log.e(tag, "runDebugAction error:", err)
                    }
                }
            }
        }
    }
}
