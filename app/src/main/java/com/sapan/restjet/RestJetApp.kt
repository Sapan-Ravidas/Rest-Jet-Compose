package com.sapan.restjet

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sapan.restjet.screen.CollectionScreen
import com.sapan.restjet.screen.RequestInputScreen
import com.sapan.restjet.screen.Route
import com.sapan.restjet.ui.compose.AppBar
import com.sapan.restjet.ui.compose.BottomNavigationBar
import com.sapan.restjet.ui.compose.FAB

@Composable
fun RestJetApp() {
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            AppBar(
                onNavigationClick = {

                },
                onAvatarClick = {

                }
            )
        },
        bottomBar = {
            BottomNavigationBar("Home", {})
        },
        floatingActionButton = {
            FAB(onClick = {})
        },
        modifier = Modifier.padding(start = 8.dp, end = 8.dp)
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Route.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Route.Home.route) {
                RequestInputScreen()
            }

            composable(Route.Collection.route) {
                CollectionScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RestJetAppPreview() {
    RestJetApp()
}
