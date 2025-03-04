package fr.isen.goetz.isensmartcompanion

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.gson.Gson
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsOff
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.material3.Card
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import androidx.work.workDataOf
import java.util.concurrent.TimeUnit
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.*

class EventDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val jsonEvent = intent.getStringExtra("event_json") // Retrieve event data as JSON
        val event = Gson().fromJson(jsonEvent, Event::class.java) // Convert JSON to Event object

        setContent {
            EventDetailScreen(event = event)
        }
    }
}

@Composable
fun EventDetailScreen(event: Event) {
    var isReminderSet by remember { mutableStateOf(false) }
    val context = LocalContext.current // Context for accessing ReminderPreferences
    val reminderPreferences = ReminderPreferences(context)

    // Load reminder state from SharedPreferences
    LaunchedEffect(event.id) {
        isReminderSet = reminderPreferences.getReminder(event.id)
    }

    // Navigation Controller
    val navController = rememberNavController()

    // Layout for Event Detail Screen
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.9f)),
                    startY = 0f,
                    endY = Float.POSITIVE_INFINITY
                )
            )
            .padding(16.dp)
    ) {
        // Row to align the Back Button and Reminder Icon
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween // Space them apart
        ) {
            // Back Button
            Button(
                onClick = {
                    val eventJson = Gson().toJson(event)
                    navController.navigate("eventDetail/$eventJson")
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD00000)) // Set the button color to red
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Retour aux événements",
                    tint = Color.White
                )
            }

            // Reminder toggle icon button
            IconButton(onClick = {
                isReminderSet = !isReminderSet // Toggle reminder state

                // Save reminder preference in SharedPreferences
                reminderPreferences.saveReminder(event.id, isReminderSet)

                // Show toast notification to the user
                Toast.makeText(
                    context,
                    if (isReminderSet) "Notification activée!" else "Notification désactivée!",
                    Toast.LENGTH_SHORT
                ).show()

                // Schedule notification if reminder is set
                if (isReminderSet) {
                    // Pass context to scheduleNotification
                    scheduleNotification(context, event.id)
                }
            }) {
                Icon(
                    imageVector = if (isReminderSet) Icons.Filled.Notifications else Icons.Filled.NotificationsOff,
                    contentDescription = "Reminder",
                    modifier = Modifier.size(32.dp)
                )
            }
        }
            Spacer(modifier = Modifier.height(10.dp))

            // Event Details Card
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = event.title,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

            Divider(
                color = Color(0xFFD00000),
                thickness = 4.dp,
                modifier = Modifier.padding(top = 6.dp)
            )

            Spacer(modifier = Modifier.height(15.dp))

        // "Catégorie" below the red divider and horizontally centered
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0x80808080))
        ) {
            Text(
                text = "Catégorie: ${event.category}",
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Row for "Date" (left side) and "Lieu" (right side) under the red divider
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Date with an icon on the left
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.DateRange,
                    contentDescription = "Date",
                    modifier = Modifier.size(22.dp),
                    //tint = Color(0xFFD00000)
                    tint = Color.Gray

                )
                Text(
                    text = "${event.date}",
                    fontSize = 16.sp
                )
            }

            // Lieu on the right
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = "Lieu",
                    modifier = Modifier.size(22.dp),
                    //tint = Color(0xFFD00000)
                    tint = Color.Gray
                )
                Text(
                    text = "${event.location}",
                    fontSize = 16.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Descriptive text remains unchanged
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0x80D00000))
        ) {
            Text(
                text = event.description,
                fontSize = 18.sp,
                fontStyle = FontStyle.Italic,
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            )
        }
        /*Text(
            text = event.description,
            fontSize = 18.sp,
            fontStyle = FontStyle.Italic,
            color = Color.Gray
        )*/
    }
}

fun scheduleNotification(context: Context, eventId: String) {
    // Create input data for the worker (passing eventId)
    val inputData = workDataOf("eventId" to eventId)

    // Create a one-time work request with a 10-second delay
    val notificationWorkRequest: WorkRequest =
        OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInitialDelay(10, TimeUnit.SECONDS)  // Delay of 10 seconds
            .setInputData(inputData)
            .build()

    // Enqueue the work request to schedule the notification
    WorkManager.getInstance(context).enqueue(notificationWorkRequest)
}