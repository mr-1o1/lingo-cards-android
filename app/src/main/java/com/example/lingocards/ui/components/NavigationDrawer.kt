package com.example.lingocards.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.lingocards.ui.screens.SettingsScreen

@Composable
fun NavigationDrawer(
    modifier: Modifier = Modifier,
    navController: NavController,
    isDarkMode: Boolean,
    onToggleTheme: () -> Unit,
    content: @Composable () -> Unit
) {
    ModalNavigationDrawer(
        drawerState = rememberDrawerState(DrawerValue.Closed),
        drawerContent = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = "Menu",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Navigation Items
                NavigationDrawerItem(
                    label = { Text("Home") },
                    selected = false,
                    onClick = {
                        navController.navigate("home")
                    }
                )

                NavigationDrawerItem(
                    label = { Text("Settings") },
                    selected = false,
                    onClick = {
                        navController.navigate("settings")
                    }
                )
            }
        },
        content = {
            Box(modifier = Modifier.fillMaxSize()) {
                // Main content goes here
                content()
            }
        }
    )
}
