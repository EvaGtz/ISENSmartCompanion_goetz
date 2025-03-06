package fr.isen.goetz.isensmartcompanion

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class NotificationWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val eventId = inputData.getString("eventId") ?: return Result.failure()

        //Get the reminder preference for this event
        val reminderPreferences = ReminderPreferences(applicationContext)
        val isReminderSet = reminderPreferences.getReminder(eventId)

        if (isReminderSet) {
            //Create the notification channel
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    "event_channel",  //Channel ID
                    "Event Notifications", //Channel Name
                    NotificationManager.IMPORTANCE_HIGH //Importance level
                ).apply {
                    description = "Channel for event reminders"
                }
                val notificationManager =
                    applicationContext.getSystemService(NotificationManager::class.java)
                notificationManager?.createNotificationChannel(channel)
            }

            // Create and show the notification
            val notificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val notification = NotificationCompat.Builder(applicationContext, "event_channel")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Event Reminder")
                .setContentText("The event you subscribed to is coming up!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build()

            notificationManager.notify(eventId.hashCode(), notification)
        }
        return Result.success()
    }
}