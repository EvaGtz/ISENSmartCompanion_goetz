package fr.isen.goetz.isensmartcompanion

import android.content.Context
import android.content.SharedPreferences

class ReminderPreferences(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("user_preferences", Context.MODE_PRIVATE)

    //Save the reminder status (true/false) for an event
    fun saveReminder(eventId: String, isReminderSet: Boolean) {
        sharedPreferences.edit().putBoolean(eventId, isReminderSet).apply()
    }

    //Retrieve the reminder status for a specific event
    fun getReminder(eventId: String): Boolean {
        return sharedPreferences.getBoolean(eventId, false) //Default to false if not set
    }
}