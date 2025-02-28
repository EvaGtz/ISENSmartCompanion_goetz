package fr.isen.goetz.isensmartcompanion

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons

@Composable
fun HistoryScreen() {
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
    LazyColumn {
        items(interactions.value) { interaction ->
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
                        text = "Date: ${java.text.SimpleDateFormat("dd/MM/yyyy HH:mm", java.util.Locale.getDefault()).format(Date(interaction.date))}",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
            //Delete button
            IconButton(
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        interactionDao.deleteInteraction(interaction)
                        interactions.value = interactionDao.getAllInteractions()
                    }
                }
            ) {
                Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete Interaction")
            }
            //To clear all the history
            IconButton(
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        interactionDao.deleteAllInteractions()
                        interactions.value = emptyList()  // Clear the list in UI
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete All History"
                )
            }
        }
    }
}

//Retrieve interactions from Room
val interactions = remember { mutableStateOf<List<Interaction>>(emptyList()) }

LaunchedEffect(Unit) {
    CoroutineScope(Dispatchers.IO).launch {
        interactions.value = interactionDao.getAllInteractions()
    }
}


