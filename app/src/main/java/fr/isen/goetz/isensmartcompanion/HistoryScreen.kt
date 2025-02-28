package fr.isen.goetz.isensmartcompanion

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

@Composable
fun HistoryScreen(interactionDao: InteractionDao) {
    // Collect interactions from the Room database using Flow
    val interactions by interactionDao.getAllInteractions().collectAsState(initial = emptyList())

    // Get the coroutine scope to launch coroutines within this composable
    val coroutineScope = rememberCoroutineScope()



    //Box(
    //    modifier = Modifier.fillMaxSize(),
    //    contentAlignment = Alignment.Center
    //) {
    //    Text(
    //        text = "Historique des événements",
    //        fontSize = 20.sp,
    //        fontWeight = FontWeight.Bold
    //    )
    //}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Historique des événements",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Delete all history button
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
                tint = Color.Red
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // List of interactions
        LazyColumn {
            items(interactions) { interaction ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { /* Handle click if needed */ },
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFD00000))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = interaction.question,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = interaction.answer,
                            fontSize = 14.sp,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Date: ${
                                SimpleDateFormat(
                                    "dd/MM/yyyy HH:mm",
                                    Locale.getDefault()
                                ).format(Date(interaction.date))
                            }",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }

                //Delete button
                IconButton(
                    onClick = {
                        coroutineScope.launch(Dispatchers.IO) {
                            interactionDao.deleteInteraction(interaction)
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete Interaction"
                    )
                }
            }
        }
    }
}