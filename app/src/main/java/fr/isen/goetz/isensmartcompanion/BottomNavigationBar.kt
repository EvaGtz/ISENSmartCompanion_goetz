package fr.isen.goetz.isensmartcompanion

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.ManageHistory
import androidx.compose.material.icons.filled.ViewAgenda
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    // Add "agenda" to the items, icons, and labels
    val items = listOf("home", "events", "history", "agenda")
    val icons = listOf(
        Icons.Filled.Home,
        Icons.Filled.Event,
        Icons.Filled.ManageHistory,
        Icons.Filled.ViewAgenda
    )
    val labels = listOf("ACCUEIL", "EVENEMENTS", "HISTORIQUE", "AGENDA")

    var selectedItem by rememberSaveable { mutableStateOf(0) }

    NavigationBar(
        containerColor = Color.Black
    ) {
        items.forEachIndexed { index, screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = icons[index],
                        contentDescription = labels[index],
                        tint = if (selectedItem == index) Color(0xFFD00000) else Color.White
                    )
                },
                label = {
                    Text(
                        text = labels[index],
                        color = if (selectedItem == index) Color(0xFFD00000) else Color.White
                    )
                },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    navController.navigate(screen)
                },
                // Remove background indicator color
                colors = androidx.compose.material3.NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFFD00000),  // Red for selected icon
                    unselectedIconColor = Color.White,      // White for unselected icon
                    indicatorColor = Color.Transparent      // Removes the gray background
                )
            )
        }
    }
}