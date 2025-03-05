package fr.isen.goetz.isensmartcompanion

import AgendaViewModel
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier) {
    // Get the context within the composable function using LocalContext.current
    val context = LocalContext.current

    // Get the database and the DAO here
    val database = AppDatabase.getDatabase(context)
    val interactionDao = database.interactionDao()

    // Define the NavHost with navigation actions
    NavHost(navController, startDestination = "home", modifier = modifier) {
        composable("home") { MainScreen() }
        composable("events") { EventsScreen(navController) }
        composable("agenda") {
            val agendaViewModel: AgendaViewModel = viewModel()

            AgendaScreen(
                courses = agendaViewModel.courses,
                events = listOf(
                    AgendaEvent("Hackathon", "March 1"),
                    AgendaEvent("Workshop", "March 5")
                ),
                onAddCourse = { newCourse -> agendaViewModel.addCourse(newCourse) }
            )
        }
        composable("history") {
            HistoryScreen(interactionDao)
        }
    }
}