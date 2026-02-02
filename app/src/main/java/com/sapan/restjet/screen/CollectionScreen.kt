package com.sapan.restjet.screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sapan.restjet.data.CollectionData
import com.sapan.restjet.ui.compose.CollectionCard

@Composable
fun CollectionScreen(
    list: List<CollectionData> = emptyList(),
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(list ) { item ->
            CollectionCard(item)
        }
    }
}


val items = listOf<CollectionData>(
    CollectionData("Auth", "Authentication related APIs"),
    CollectionData("Cart", "Cart related APIs"),
)

@Preview(showBackground = true)
@Composable
fun CollectionScreenPreview() {
    CollectionScreen(
        list = items
    )
}
