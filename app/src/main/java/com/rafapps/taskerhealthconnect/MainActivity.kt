package com.rafapps.taskerhealthconnect

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.isVisible
import androidx.health.connect.client.PermissionController
import androidx.lifecycle.lifecycleScope
import com.rafapps.taskerhealthconnect.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val repository by lazy { HealthConnectRepository(this) }

    private val permissionsLauncher =
        registerForActivityResult(
            PermissionController.createRequestPermissionResultContract()
        ) { granted ->
            if (granted.containsAll(repository.permissions))
                onPermissionGranted()
            else
                onPermissionDenied()
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        checkHealthConnectAndPermissions()
    }

    private fun checkHealthConnectAndPermissions() {
        if (!repository.isAvailable())
            onHealthConnectUnavailable()
        else
            lifecycleScope.launch {
                if (repository.hasPermissions())
                    onPermissionGranted()
                else
                    onPermissionDenied()
            }
    }

    private fun requestPermission() = permissionsLauncher.launch(repository.permissions)

    private fun onHealthConnectUnavailable() {
        with(binding) {
            textView.text = getString(R.string.health_connect_unavailable)
            button.text = getString(R.string.install)
            button.isVisible = true
            button.setOnClickListener { repository.installHealthConnect() }
        }
    }

    private fun onPermissionGranted() {
        with(binding) {
            textView.text = getString(R.string.permissions_granted)
            button.isVisible = false
        }
    }

    private fun onPermissionDenied() {
        with(binding) {
            textView.text = getString(R.string.permissions_not_granted)
            button.text = getString(R.string.grant_permissions)
            button.isVisible = true
            button.setOnClickListener { requestPermission() }
        }
    }
}
