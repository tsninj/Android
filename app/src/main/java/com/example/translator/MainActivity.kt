package com.example.translator

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ViewModelProvider
import com.example.translator.data.ConfigRepository
import com.example.translator.ui.navigation.InventoryNavHost
import com.example.translator.ui.theme.TranslatorTheme
import com.example.translator.ui.navigation.NavViewModel
import com.example.translator.worker.*
import com.example.translator.data.AppDataContainer
import com.example.translator.worker.TranslatorReminderWorker

class MainActivity : ComponentActivity() {
    private lateinit var navViewModel: NavViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        ANDRIOD 13 болон түүнээс дээш
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val requestPermissionLauncher = registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    Log.d("MainActivity", "Notification permission granted")
                } else {
                    Log.d("MainActivity", "Notification permission denied")
                }
            }
            requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        }

        val wordRepository = AppDataContainer(applicationContext).wordRepository
        navViewModel = ViewModelProvider(
            this, object : ViewModelProvider.Factory {
                override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                    return NavViewModel(wordRepository) as T
                }
            }
        )[NavViewModel::class.java]

        val configRepository = ConfigRepository(applicationContext)
        setContent {
            TranslatorTheme {
                InventoryNavHost(
                    navViewModel = navViewModel,
                    configRepository = configRepository
                )
            }
        }

        val lifecycleObserver = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_STOP) {
                TranslatorReminderWorker.scheduleReminderMin(this)
//                TranslatorReminderWorker.scheduleReminder24hour(this)
            } else if (event == Lifecycle.Event.ON_RESUME) {
                TranslatorReminderWorker.cancelReminder(this)
            }
        }


        lifecycle.addObserver(lifecycleObserver)

    }}

