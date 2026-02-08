package com.sapan.restjet.screen

import android.text.TextUtils
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices.NEXUS_10
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sapan.restjet.db.entity.SavedRequestData
import com.sapan.restjet.ui.compose.CollectionCard
import com.sapan.restjet.ui.compose.SavedRequestItem
import com.sapan.restjet.viewmodel.CollectionViewModel
import com.sapan.restjet.viewmodel.RequestResponseViewModel

@Composable
fun LargeScreenLayout(
    requestResponseViewModel: RequestResponseViewModel = hiltViewModel(),
    collectionViewModel: CollectionViewModel = hiltViewModel()
) {
    var selectedCollection by rememberSaveable { mutableStateOf<String?>(null) }
    var selectedRequest by rememberSaveable { mutableStateOf<SavedRequestData?>(null) }
    var showResponseScreen by rememberSaveable { mutableStateOf(false) }
    var showCreateCollectionDialog by rememberSaveable { mutableStateOf(false) }
    var showSaveFileDialog by rememberSaveable { mutableStateOf(false) }
    val lifecycleOwner = LocalLifecycleOwner.current

    Row(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.weight(0.25f)
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.surface)
                .padding(8.dp)
        ) {
            Text(
                text = "Collections",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            IconButton(
                onClick = {
                    showCreateCollectionDialog = true
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Collection")
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            // Collection list
            val collections by collectionViewModel.collections.collectAsStateWithLifecycle()
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(collections) { collection ->
                    CollectionCard(
                        collectionInfo = collection,
                        onCollectionClicked = {
                            selectedCollection = collection.title
                            collectionViewModel.loadSavedRequests(collection.title)
                        },
                        onCollectionDeleteClicked = {
                            collectionViewModel.deleteCollection(collection)
                        }
                    )
                }
            }
        }

        /**
         *
         */
        Column(
            modifier = Modifier.weight(0.25f)
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(8.dp)
        ) {
            if (selectedCollection != null) selectedCollection else "Rest Jet".let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            // saved request list
            val savedRequests by collectionViewModel.savedRequests.collectAsStateWithLifecycle()
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(savedRequests) { request ->
                    SavedRequestItem(
                        request = request,
                        onClick = {
                            selectedRequest = request
                        },
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }

        /**
         *
         */
        Column(
            modifier = Modifier.weight(0.5f)
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.background)
        ) {
            var selectedTab by remember { mutableStateOf(0) }
            val tabs = listOf("Request", "Response")
            TabRow(
                selectedTabIndex = selectedTab
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) },
                        selected = selectedTab == index,
                        onClick = { selectedTab = index }
                    )
                }
            }

            when (selectedTab) {
                0 -> {
                    RequestInputScreen(
                        viewModel = requestResponseViewModel,
                        onSendRequest =  {
                            showResponseScreen = true
                            selectedTab = 1
                        },
                        onSaveRequest =  {
                            showSaveFileDialog = true
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                    )
                }

                1 -> {
                    ResponseScreen(
                        viewModel = requestResponseViewModel,
                        navController = null,
                        modifier = Modifier.fillMaxSize().padding(8.dp)
                    )
                }
            }
        }

        if (showCreateCollectionDialog) {
            KeyValueInputScreen(
                onSubmit = { title, description ->
                    if (!TextUtils.isEmpty(title)) {
                        collectionViewModel.addCollection(title, description)
                        showCreateCollectionDialog = false
                    }
                },
                onDismiss = {
                    showCreateCollectionDialog = false
                },
                title = "ADD COLLECTION",
                keyLabel = "Title/Filename",
                valueLabel = "Description"
            )
        }

        if (showSaveFileDialog) {
            PopupDialog(
                onDismissRequest = {
                    showSaveFileDialog = false
                },
                onConfirm = { filename ->
                    if (selectedCollection != null) {
                        requestResponseViewModel.saveRequest(selectedCollection!!, filename)
                    } else {
                        showCreateCollectionDialog = true
                    }

                    showSaveFileDialog = false
                }
            )
        }
    }
}

@Preview(showBackground = true, device = NEXUS_10)
@Composable
fun LargeScreenPreview() {
    LargeScreenLayout()
}