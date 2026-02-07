package com.sapan.restjet.screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.sapan.restjet.db.entity.CollectionData
import com.sapan.restjet.ui.compose.CollectionCard
import com.sapan.restjet.viewmodel.CollectionViewModel

@Composable
fun CollectionScreen(
    onCollectionItemClicked: (CollectionData) -> Unit,
    viewModel: CollectionViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val collections by viewModel.collections.collectAsState()

    LazyColumn(
        modifier = modifier
    ) {
        items(items = collections) { item ->
            CollectionCard(item,
                onCollectionClicked = { collection ->
                    onCollectionItemClicked(collection)
                },
                onCollectionDeleteClicked = { collection ->
                    viewModel.deleteCollection(collection)
                }
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun CollectionScreenPreview() {
    CollectionScreen(
        onCollectionItemClicked = {}
    )
}
