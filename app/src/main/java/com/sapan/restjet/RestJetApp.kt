package com.sapan.restjet

import android.text.TextUtils
import androidx.compose.foundation.gestures.DraggableState
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.sapan.restjet.screen.KeyValueInputScreen
import com.sapan.restjet.screen.RequestInputScreen
import com.sapan.restjet.screen.ResponseScreen
import com.sapan.restjet.screen.Route
import com.sapan.restjet.screen.SavedRequestDrawer
import com.sapan.restjet.ui.compose.AppBar
import com.sapan.restjet.ui.compose.BottomNavigationBar
import com.sapan.restjet.ui.compose.SpeedDialFab
import com.sapan.restjet.viewmodel.CollectionViewModel
import com.sapan.restjet.viewmodel.RequestResponseViewModel
import kotlinx.coroutines.launch

@Composable
fun RestJetApp(
    requestResponseViewModel: RequestResponseViewModel = hiltViewModel(),
    collectionViewModel: CollectionViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRouteString = navBackStackEntry?.destination?.route ?: Route.Home().route

    val currentRoute = when {
        currentRouteString.startsWith("Home/") -> {
            val title = navBackStackEntry?.arguments?.getString("title") ?: "Rest Jet"
            Route.Home(title)
        }
        currentRouteString == Route.Response.route -> Route.Response
        currentRouteString == Route.Collection.route -> Route.Collection
        else -> Route.Home()
    }

    var showCollectionDialog by rememberSaveable { mutableStateOf(false) }
    var drawerState by remember { mutableStateOf(DrawerState(DrawerValue.Closed)) }
    val scope = rememberCoroutineScope()

    val fabSpeedDialCollectionScreen = listOf<SpeedDial>(
        SpeedDial("+ Collection") {
            showCollectionDialog = true
        }
    )

    val fabSpeedDialHomeScreen = listOf<SpeedDial>(
        SpeedDial("+ Request") {
            navController.navigate(Route.Home().route)
        },
        SpeedDial("+ Collection") {
            showCollectionDialog = true
        }
    )

    SavedRequestDrawer(
        drawerState = drawerState,
        onCloseDrawer = {

        },
        onRequestClick = {
        },
        viewModel = collectionViewModel
    ) {
        Scaffold(
            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
            topBar = {
                AppBar(
                    title = currentRoute.title,
                    onNavigationClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    },
                    onAvatarClick = {

                    }
                )
            },
            bottomBar = {
                BottomNavigationBar(
                    selectedItem = currentRoute.route,
                    onSelectedItem = { target ->
                        if (target != currentRoute.route) {
                            navController.navigate(target)
                        }
                    }
                )
            },
            floatingActionButton = {
                SpeedDialFab(items = if (currentRoute is Route.Collection) fabSpeedDialCollectionScreen else fabSpeedDialHomeScreen)
            }
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = Route.Home().route,
                modifier = Modifier.padding(paddingValues)
            ) {
                composable(Route.Home().route) {
                    RequestInputScreen(
                        viewModel = requestResponseViewModel,
                        onSendRequest = {
                            navController.navigate(Route.Response.route)
                        },
                        onSaveCollection = {

                        }
                    )
                }

                composable(Route.Collection.route) {
                    CollectionScreen(
                        viewModel = collectionViewModel,
                        onCollectionItemClicked = { collection ->
                            val route = "Home/${collection.title}"
                            navController.navigate(route)
                        }
                    )
                }

                composable(Route.Response.route) {
                    ResponseScreen(
                        viewModel = requestResponseViewModel
                    )
                }
            }

            if (showCollectionDialog) {
                KeyValueInputScreen(
                    onSubmit = { title, description ->
                        if (!TextUtils.isEmpty(title)) {
                            collectionViewModel.addCollection(title, description)
                            showCollectionDialog = false
                        }
                    },
                    onDismiss = {
                        showCollectionDialog = false
                    },
                    title = "ADD COLLECTION",
                    keyLabel = "Title/Filename",
                    valueLabel = "Description"
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
