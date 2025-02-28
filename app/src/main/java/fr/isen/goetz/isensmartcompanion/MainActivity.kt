package fr.isen.goetz.isensmartcompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import fr.isen.goetz.isensmartcompanion.ui.theme.ISENSmartCompanionTheme
import androidx.compose.foundation.layout.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ISENSmartCompanionTheme {
                val navController = rememberNavController()
                Scaffold(bottomBar = { BottomNavigationBar(navController) }) { innerPadding ->
                    NavigationGraph(navController, Modifier.padding(innerPadding))
                }
            }
        }
    }
}