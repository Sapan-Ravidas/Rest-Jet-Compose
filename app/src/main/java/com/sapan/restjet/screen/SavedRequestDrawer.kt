package com.sapan.restjet.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sapan.restjet.db.entity.SavedRequestData
import com.sapan.restjet.ui.compose.SavedRequestItem
import com.sapan.restjet.ui.theme.Typography
import com.sapan.restjet.viewmodel.CollectionViewModel
import kotlinx.coroutines.launch

@Composable
fun SavedRequestDrawer(
    drawerState: DrawerState,
    onCloseDrawer:() -> Unit,
    onRequestClick: (SavedRequestData) -> Unit,
    viewModel: CollectionViewModel = hiltViewModel(),
    content: @Composable () -> Unit
) {
    val savedRequests by viewModel.savedRequests.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(300.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                ) {
                    IconButton(
                        onClick = {
                            scope.launch {
                                try {
                                    drawerState.close()
                                    onCloseDrawer()
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }
                        },
                        modifier = Modifier.align(
                            alignment = androidx.compose.ui.Alignment.TopEnd
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close drawer"
                        )
                    }

                    Text("Workspace",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(top = 8.dp, bottom = 16.dp)
                        )
                }

                LazyColumn(
                    modifier = Modifier.fillMaxHeight()
                        .padding(horizontal = 16.dp)
                ) {
                    if (savedRequests.isEmpty()) {
                        item {
                            Text(text = "Workspace is Empty", style = Typography.titleLarge)
                        }
                    } else {
                        items(items = savedRequests) { request ->
                            SavedRequestItem(
                                request = request,
                                onClick = {
                                    scope.launch {
                                        drawerState.close()
                                        onCloseDrawer()
                                        onRequestClick(it)
                                    }
                                },
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    }
                }

            }
        }
    ) {
        content()
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SavedRequestDrawerPreview() {
    SavedRequestDrawer(
        drawerState = DrawerState(DrawerValue.Open),
        onCloseDrawer = {},
        onRequestClick = {}
    ) {

    }
}