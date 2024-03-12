package com.rafapps.taskerhealthconnect.tasker

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.health.connect.client.PermissionController
import androidx.lifecycle.lifecycleScope
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfig
import com.joaomgcd.taskerpluginlibrary.input.TaskerInput
import com.rafapps.taskerhealthconnect.HealthConnectRepository
import com.rafapps.taskerhealthconnect.R
import com.rafapps.taskerhealthconnect.databinding.ActivityGetDataBinding
import kotlinx.coroutines.launch

class GetHealthDataActivity : AppCompatActivity(), TaskerPluginConfig<GetHealthDataInput> {

    private lateinit var binding: ActivityGetDataBinding
    private val repository by lazy { HealthConnectRepository(this) }
    private val taskerHelper by lazy { GetHealthDataActionHelper(this) }

    private val permissionsLauncher =
        registerForActivityResult(
            PermissionController.createRequestPermissionResultContract()
        ) { granted ->
            if (granted.containsAll(repository.permissions))
                onPermissionGranted()
        }

    override val context: Context
        get() = this

    override val inputForTasker: TaskerInput<GetHealthDataInput>
        get() = TaskerInput(
            GetHealthDataInput(days = runCatching {
                binding.daysText.editText?.text.toString().toLong()
            }.getOrDefault(0))
        )

    override fun assignFromInput(input: TaskerInput<GetHealthDataInput>) {
        binding.daysText.editText?.setText(input.regular.days.toString())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityGetDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
}
