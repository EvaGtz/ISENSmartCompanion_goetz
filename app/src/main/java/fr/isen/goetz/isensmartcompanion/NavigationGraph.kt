package fr.isen.goetz.isensmartcompanion

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier) {
    //Get the context using LocalContext.current
    val context = LocalContext.current

    //Get the database and the DAO
    val database = AppDatabase.getDatabase(context)
    val interactionDao = database.interactionDao()

    //Define the NavHost with navigation actions
    NavHost(navController, startDestination = "home", modifier = modifier) {
        composable("home") { MainScreen() }
        composable("events") { EventsScreen(navController) }
        composable("agenda") {
            val sampleCourses = emptyList<Course>()

            val sampleEvents = emptyList<AgendaEvent>()

            //Passing courses and events to AgendaScreen
            AgendaScreen(courses = sampleCourses, events = sampleEvents)
        }
        composable("history") {
            HistoryScreen(interactionDao)
        }
    }
}