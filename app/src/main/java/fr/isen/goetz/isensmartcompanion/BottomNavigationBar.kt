package fr.isen.goetz.isensmartcompanion

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
//import androidx.compose.runtime.rememberSaveable
import androidx.navigation.NavHostController
import androidx.compose.runtime.mutableStateOf
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.History

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf("home", "events", "history")
    val icons = listOf(Icons.Filled.Home, Icons.Filled.CalendarToday, Icons.Filled.History)
    val labels = listOf("ACCUEIL", "EVENEMENTS", "HISTORIQUE")

    var selectedItem = 0 // Remove rememberSaveable and mutableStateOf temporarily

    NavigationBar {
        items.forEachIndexed { index, screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = icons[index],
                        contentDescription = labels[index],
                        tint = if (selectedItem == index) Color(0xFFD00000) else Color.Gray
                    )
                },
                label = {
                    Text(
                        text = labels[index],
                        color = if (selectedItem == index) Color(0xFFD00000) else Color.Gray
                    )
                },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    navController.navigate(screen)
                }
            )
        }
    }
}