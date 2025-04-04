package com.example.translator.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager

import androidx.work.WorkerParameters
import com.example.translator.MainActivity
import com.example.translator.R
import java.util.concurrent.TimeUnit

class TranslatorReminderWorker(context: Context, workerParams: WorkerParameters) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        showNotification()
        return Result.success()
    }

    private fun showNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "notification",
                "notification",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "notification of apps"
            }
            val notificationManager: NotificationManager =
                applicationContext.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }

        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            applicationContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(applicationContext, "notification")
            .setSmallIcon(R.drawable.open_sky)
            .setContentTitle("Үгээ харах цаг боллоо!")
            .setContentText("Апп руу орж дарна уу.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setStyle(NotificationCompat.BigTextStyle().bigText("Өнөөдөр шинэ англи үг сурцгаая"))
            .setContentIntent(pendingIntent)
            .build()

        NotificationManagerCompat.from(applicationContext).notify(1, notification)
    }

    companion object {
        private const val CHANNEL_ID = "notification"

        fun scheduleReminderMin(context: Context) {
            val workrequest = PeriodicWorkRequestBuilder<TranslatorReminderWorker>(15, TimeUnit.MINUTES).build()
            WorkManager.getInstance(context)
                .enqueueUniquePeriodicWork(CHANNEL_ID, ExistingPeriodicWorkPolicy.UPDATE, workrequest)
        }
        fun scheduleReminder24hour(context: Context) {
            val workrequest = PeriodicWorkRequestBuilder<TranslatorReminderWorker>(24, TimeUnit.HOURS)
                .setInitialDelay(24, TimeUnit.HOURS)
                .build()
            WorkManager.getInstance(context)
                .enqueueUniquePeriodicWork(CHANNEL_ID, ExistingPeriodicWorkPolicy.UPDATE, workrequest)
        }

        fun cancelReminder(context: Context) {
            WorkManager.getInstance(context).cancelUniqueWork(CHANNEL_ID)
        }
    }
}
