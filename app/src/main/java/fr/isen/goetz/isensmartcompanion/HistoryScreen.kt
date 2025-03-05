package fr.isen.goetz.isensmartcompanion

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.clickable
import androidx.compose.ui.text.style.TextAlign
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.runtime.*
import androidx.compose.material.icons.filled.Delete
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.border
import androidx.compose.ui.graphics.Brush

@Composable
fun HistoryScreen(interactionDao: InteractionDao) {
    // Collect interactions from the Room database using Flow
    val interactions by interactionDao.getAllInteractions().collectAsState(initial = emptyList())

    // Get the coroutine scope to launch coroutines within this composable
    val coroutineScope = rememberCoroutineScope()

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
            .padding(16.dp),
    ) {
        Text(
            text = "HISTORIQUE CONVERSATIONS",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Divider(
            color = Color(0xFFD00000),
            thickness = 2.dp,
            modifier = Modifier.padding(top = 4.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Delete all history button
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    coroutineScope.launch(Dispatchers.IO) {
                        interactionDao.deleteAllInteractions()
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete All History",
                    tint = Color(0xFFD00000)
                )
            }
            Text(
                text = "Supprimer tout l'historique",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFD00000)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // List of interactions
        LazyColumn {
            items(interactions) { interaction ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                        .border(1.dp, Color.Black, shape = RoundedCornerShape(8.dp)),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.1f)
                    )
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) { // Box allows overlaying elements
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = interaction.question,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = interaction.answer,
                                fontSize = 16.sp,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(3.dp))
                            Text(
                                text = "Date: ${
                                    SimpleDateFormat(
                                        "dd/MM/yyyy HH:mm",
                                        Locale.getDefault()
                                    ).format(Date(interaction.date))
                                }",
                                fontSize = 14.sp,
                                color = Color.White
                            )
                        }

                        // Delete button positioned at top-right
                        IconButton(
                            onClick = {
                                coroutineScope.launch(Dispatchers.IO) {
                                    interactionDao.deleteInteraction(interaction)
                                }
                            },
                            modifier = Modifier
                                .align(Alignment.TopEnd) // Moves the icon to the top-right
                                .padding(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "Delete Interaction",
                                tint = Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }
}
