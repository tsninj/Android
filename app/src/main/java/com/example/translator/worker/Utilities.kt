package com.example.translator.worker

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

fun scheduleReminder(context: Context) {
    val request= PeriodicWorkRequestBuilder<WordReminderWorker>(15,TimeUnit.MINUTES).build()
    WorkManager.getInstance(context)
        .enqueueUniquePeriodicWork("word_reminder", ExistingPeriodicWorkPolicy.UPDATE, request)
}
fun cancelReminder(context: Context) {
    val workManager = WorkManager.getInstance(context)
    workManager.cancelUniqueWork("word_reminder")
}

