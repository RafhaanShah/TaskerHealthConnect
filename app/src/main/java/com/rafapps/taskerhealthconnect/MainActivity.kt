package com.rafapps.taskerhealthconnect

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.isVisible
import androidx.health.connect.client.PermissionController
import androidx.lifecycle.lifecycleScope
import com.rafapps.taskerhealthconnect.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import androidx.core.net.toUri

class MainActivity : AppCompatActivity() {

    private val tag = "MainActivity"
    private val releaseUrl = "https://github.com/RafhaanShah/TaskerHealthConnect/releases"
    private val apiUrl = "https://developer.android.com/reference/kotlin/androidx/health/connect/client/package-summary"
    private lateinit var binding: ActivityMainBinding
    private val repository by lazy { HealthConnectRepository(this) }
    private val allPermissions by lazy { repository.readPermissions + repository.writePermissions }
    private val permissionsLauncher =
        registerForActivityResult(
            PermissionController.createRequestPermissionResultContract()
        ) { _ -> onPermissionStateUpdated() }


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(tag, "onCreate")
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.textViewVersion.text = getString(R.string.version, BuildConfig.VERSION_NAME)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.check_updates -> {
                Log.d(tag, "onOptionsItemSelected check_updates")
                runCatching { startActivity(Intent(Intent.ACTION_VIEW, releaseUrl.toUri())) }
                true
            }
            R.id.health_connect_api -> {
                Log.d(tag, "onOptionsItemSelected health_connect_api")
                runCatching { startActivity(Intent(Intent.ACTION_VIEW, apiUrl.toUri())) }
                true
            }
            R.id.health_connect_toolbox -> {
                Log.d(tag, "onOptionsItemSelected health_connect_toolbox")
                repository.openHealthConnectToolbox()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        checkAvailabilityAndPermissions()
    }

    private fun checkAvailabilityAndPermissions() {
        if (!repository.isAvailable())
            onHealthConnectUnavailable()
        else
            onPermissionStateUpdated()
    }

    private fun onPermissionStateUpdated() {
        lifecycleScope.launch {
            if (repository.hasPermissions(allPermissions))
                onPermissionGranted()
            else
                onPermissionDenied()
        }
    }

    private fun requestPermission() = permissionsLauncher.launch(allPermissions)

    private fun onHealthConnectUnavailable() {
        Log.d(tag, "onHealthConnectUnavailable")
        with(binding) {
            textView.text = getString(R.string.health_connect_unavailable)
            button.text = getString(R.string.install)
            button.setOnClickListener { repository.installHealthConnect() }
        }
    }

    private fun onPermissionGranted() {
        Log.d(tag, "onPermissionGranted")
        with(binding) {
            textView.text = getString(R.string.app_ready)
            button.text = getString(R.string.open)
            button.setOnClickListener { repository.openHealthConnect() }
        }
    }

    private fun onPermissionDenied() {
        Log.d(tag, "onPermissionDenied")
        with(binding) {
            textView.text = getString(R.string.permissions_not_granted)
            button.text = getString(R.string.grant_permissions)
            button.setOnClickListener { requestPermission() }
        }
    }
}
