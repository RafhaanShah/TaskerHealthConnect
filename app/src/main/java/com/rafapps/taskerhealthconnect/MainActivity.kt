package com.rafapps.taskerhealthconnect

import android.content.Intent
import android.net.Uri
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

    private val TAG = "MainActivity"
    private val releaseUrl = "https://github.com/RafhaanShah/TaskerHealthConnect/releases"
    private lateinit var binding: ActivityMainBinding
    private val repository by lazy { HealthConnectRepository(this) }
    private val allPermissions by lazy { repository.readPermissions + repository.writePermissions }
    private val permissionsLauncher =
        registerForActivityResult(
            PermissionController.createRequestPermissionResultContract()
        ) { granted ->
            if (granted.containsAll(allPermissions))
                onPermissionGranted()
            else
                onPermissionDenied()
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")
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
                Log.d(TAG, "onOptionsItemSelected check_updates")
                runCatching { startActivity(Intent(Intent.ACTION_VIEW, releaseUrl.toUri())) }
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
            lifecycleScope.launch {
                if (repository.hasPermissions(allPermissions))
                    onPermissionGranted()
                else
                    onPermissionDenied()
            }
    }

    private fun requestPermission() = permissionsLauncher.launch(allPermissions)

    private fun onHealthConnectUnavailable() {
        Log.d(TAG, "onHealthConnectUnavailable")
        with(binding) {
            textView.text = getString(R.string.health_connect_unavailable)
            button.text = getString(R.string.install)
            button.isVisible = true
            button.setOnClickListener { repository.installHealthConnect() }
        }
    }

    private fun onPermissionGranted() {
        Log.d(TAG, "onPermissionGranted")
        with(binding) {
            textView.text = getString(R.string.app_ready)
            button.isVisible = false
        }
    }

    private fun onPermissionDenied() {
        Log.d(TAG, "onPermissionDenied")
        with(binding) {
            textView.text = getString(R.string.permissions_not_granted)
            button.text = getString(R.string.grant_permissions)
            button.isVisible = true
            button.setOnClickListener { requestPermission() }
        }
    }
}
