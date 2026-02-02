package com.sapan.restjet

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sapan.restjet.data.SpeedDial
import com.sapan.restjet.screen.CollectionScreen
import com.sapan.restjet.screen.RequestInputScreen
import com.sapan.restjet.screen.ResponseScreen
import com.sapan.restjet.screen.Route
import com.sapan.restjet.ui.compose.AppBar
import com.sapan.restjet.ui.compose.BottomNavigationBar
import com.sapan.restjet.ui.compose.SpeedDialFab
import com.sapan.restjet.viewmodel.RequestResponseViewModel

@Composable
fun RestJetApp(
    viewModel: RequestResponseViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRouteString = navBackStackEntry?.destination?.route ?: Route.Home

    val currentRoute = when (currentRouteString) {
        Route.Home.route -> Route.Home
        Route.Collection.route -> Route.Collection
        else -> Route.Home
    }

    val fabSpeedDialCollectionScreen = listOf<SpeedDial>(
        SpeedDial("+ Collection") {
            navController.navigate(Route.Collection.route)
        }
    )

    val fabSpeedDialHomeScreen = listOf<SpeedDial>(
        SpeedDial("+ Request") {
            navController.navigate(Route.Home.route)
        },
        SpeedDial("+ Collection") {
            navController.navigate(Route.Collection.route)
        }
    )

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
                    if (target != currentRoute.route)
                    navController.navigate(target)
                }
            )
        },
        floatingActionButton = {
            SpeedDialFab(items = if (currentRoute is Route.Collection) fabSpeedDialCollectionScreen else fabSpeedDialHomeScreen)
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Route.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Route.Home.route) {
                RequestInputScreen(
                    viewModel = viewModel,
                    onSendRequest = {
                        navController.navigate(Route.Response.route)
                    }
                )
            }

            composable(Route.Collection.route) {
                CollectionScreen()
            }

            composable(Route.Response.route) {
                ResponseScreen(
                    viewModel = viewModel
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RestJetAppPreview() {
    RestJetApp()
}
