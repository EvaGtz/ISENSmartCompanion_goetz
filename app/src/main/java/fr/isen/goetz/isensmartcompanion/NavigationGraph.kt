package fr.isen.goetz.isensmartcompanion

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.ui.platform.LocalContext

@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier) {
    // Correct: Get the context within the composable function using LocalContext.current
    val context = LocalContext.current

    // Get the database and the DAO here
    val database = AppDatabase.getDatabase(context)
    val interactionDao = database.interactionDao()

    // Define the NavHost with navigation actions
    NavHost(navController, startDestination = "home", modifier = modifier) {
        composable("home") { MainScreen() }
        composable("events") { EventsScreen(navController) }
        // Pass interactionDao to HistoryScreen
        composable("history") {
            HistoryScreen(interactionDao)
        }
    }
}