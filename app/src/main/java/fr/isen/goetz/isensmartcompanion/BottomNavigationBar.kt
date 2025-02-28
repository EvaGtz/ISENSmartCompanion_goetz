package fr.isen.goetz.isensmartcompanion

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.ListAlt

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    // Add "agenda" to the items, icons, and labels
    val items = listOf("home", "events", "history", "agenda")
    val icons = listOf(
        Icons.Filled.Home,
        Icons.Filled.CalendarToday,
        Icons.Filled.History,
        Icons.Filled.ListAlt // Icon for the Agenda tab
    )
    val labels = listOf("ACCUEIL", "EVENEMENTS", "HISTORIQUE", "AGENDA")

    var selectedItem = 0 // Track selected item

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