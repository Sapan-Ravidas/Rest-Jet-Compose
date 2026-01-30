package com.sapan.restjet

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sapan.restjet.data.fabSpeedDialItems
import com.sapan.restjet.screen.CollectionScreen
import com.sapan.restjet.screen.RequestInputScreen
import com.sapan.restjet.screen.Route
import com.sapan.restjet.ui.compose.AppBar
import com.sapan.restjet.ui.compose.BottomNavigationBar
import com.sapan.restjet.ui.compose.FAB
import com.sapan.restjet.ui.compose.SpeedDialFab

@Composable
fun RestJetApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRouteString = navBackStackEntry?.destination?.route ?: Route.Home
    val currentRoute = when (currentRouteString) {
        Route.Home.route -> Route.Home
        Route.Collection.route -> Route.Collection
        else -> Route.Home
    }

    Scaffold(
        modifier = Modifier.padding(start = 8.dp, end = 8.dp),
        topBar = {
            AppBar(
                title = currentRoute.title,
                onNavigationClick = {

                },
                onAvatarClick = {

                }
            )
        },
        bottomBar = {
            BottomNavigationBar(
                selectedItem = currentRoute.route,
                onSelectedItem = { target ->
                    navController.navigate(target) {
                        // standard tab navigation behavior
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        },
        floatingActionButton = {
            SpeedDialFab(items = fabSpeedDialItems)
        }
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
