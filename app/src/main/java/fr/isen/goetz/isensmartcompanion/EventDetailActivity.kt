package fr.isen.goetz.isensmartcompanion

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.gson.Gson
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.material3.Card
import androidx.compose.foundation.shape.RoundedCornerShape

class EventDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val jsonEvent = intent.getStringExtra("event_json") // Retrieve event data as JSON
        val event = Gson().fromJson(jsonEvent, Event::class.java) // Convert JSON to Event object

        setContent {
            // Layout for Event Detail Screen
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Back Button
                Button(
                    onClick = { finish() }, // Close the Activity when the button is pressed
                    modifier = Modifier.padding(top = 16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD00000))
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Retour aux événements",
                        tint = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Event Details
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                        //.background(Color.Gray, shape = MaterialTheme.shapes.medium) // Set background color
                    colors = CardDefaults.cardColors(containerColor = Color.Gray),
                    shape = RoundedCornerShape(topStart = 13.dp, topEnd = 13.dp),
                    //padding =
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

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Date: ${event.date}",
                    //fontWeight = FontWeight.Light,
                    fontSize = 18.sp
                )
                Text(
                    text = "Lieu: ${event.location}",
                    //fontWeight = FontWeight.Light,
                    fontSize = 18.sp
                )
                Text(
                    text = "Catégorie: ${event.category}",
                    //fontWeight = FontWeight.Light,
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = event.description,
                    fontSize = 18.sp,
                    fontStyle = FontStyle.Italic,
                    //fontWeight = FontWeight.Light,
                    color = Color.Gray
                )
            }
        }
    }
}