package com.example.lingocards

//import com.example.lingocards.ui.screens.HomeScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.lingocards.ui.components.NavigationDrawer
import com.example.lingocards.ui.screens.HomeScreen
import com.example.lingocards.ui.theme.LingoCardsTheme
import com.example.lingocards.ui.theme.rememberThemeState

import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lingocards.ui.screens.HomeScreen
import com.example.lingocards.ui.screens.SettingsScreen
import com.example.lingocards.ui.theme.LingoCardsTheme
import com.example.lingocards.ui.components.NavigationDrawer

class MainActivity1 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isDarkMode by rememberThemeState()

            LingoCardsTheme(darkTheme = isDarkMode) {
                val navController = rememberNavController()

                // Navigation Drawer with Main Content
                NavigationDrawer(
                    navController = navController,
                    isDarkMode = isDarkMode,
                    onToggleTheme = { isDarkMode = !isDarkMode }
                ) {
                    // Main content of the app (Navigation Host)
                    NavHost(navController = navController, startDestination = "home") {
                        composable("home") {
                            HomeScreen()
                        }
                        composable("settings") {
                            SettingsScreen(
                                isDarkMode = isDarkMode,
                                onToggleTheme = { isDarkMode = !isDarkMode }
                            )
                        }
                    }
                }
            }
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LingoCardsTheme(darkTheme = false) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LingoCardsTheme {
        HomeScreen()
    }
}