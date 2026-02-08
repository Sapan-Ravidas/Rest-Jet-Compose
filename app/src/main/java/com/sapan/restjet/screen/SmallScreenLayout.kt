package com.sapan.restjet.screen

import android.text.TextUtils
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sapan.restjet.data.SpeedDial
import com.sapan.restjet.ui.compose.AppBar
import com.sapan.restjet.ui.compose.BottomNavigationBar
import com.sapan.restjet.ui.compose.SpeedDialFab
import com.sapan.restjet.viewmodel.CollectionViewModel
import com.sapan.restjet.viewmodel.RequestResponseViewModel
import kotlinx.coroutines.launch

@Composable
fun SmallScreenLayout(
    requestResponseViewModel: RequestResponseViewModel = hiltViewModel(),
    collectionViewModel: CollectionViewModel = hiltViewModel()
) {

    /**
     * NavController and NavStack
     */
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

    /**
     * show ui
     */
    var showCollectionDialog by rememberSaveable { mutableStateOf(false) }
    var drawerState by remember { mutableStateOf(DrawerState(DrawerValue.Closed)) }
    var showDialogToSaveFile by rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    /**
     * Fab Button
     */
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

    /**
     * drawer state
     */
    LaunchedEffect(drawerState.currentValue) {
        if (drawerState.isOpen) {
            val filename = navBackStackEntry?.arguments?.getString("title")
            collectionViewModel.loadSavedRequests(filename)
        }
    }


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
                        navController.navigate(target) {
                            // Pop up to the start destination of the graph to
                            // avoid building up a huge stack of destinations
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
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
                        onSaveRequest = {
                            showDialogToSaveFile = true
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
                        viewModel = requestResponseViewModel,
                        navController = navController
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

            if (showDialogToSaveFile) {
                PopupDialog(
                    onDismissRequest = {
                        showDialogToSaveFile = false
                    },
                    onConfirm = { filename ->
                        val collectionName = navBackStackEntry?.arguments?.getString("title")
                        if (collectionName != null) {
                            requestResponseViewModel.saveRequest(collectionName, filename)
                        } else {
                            showCollectionDialog = true
                        }
                        showDialogToSaveFile = false
                    }
                )
            }
        }
    }
}