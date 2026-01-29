package com.sapan.restjet.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sapan.restjet.data.CollectionInfo
import com.sapan.restjet.ui.compose.AppBar
import com.sapan.restjet.ui.compose.BottomNavigationBar
import com.sapan.restjet.ui.compose.CollectionCard
import com.sapan.restjet.ui.compose.FAB

@Composable
fun CollectionScreen(
    list: List<CollectionInfo> = listOf(),
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


val items = listOf<CollectionInfo>(
    CollectionInfo("header1", "description"),
    CollectionInfo("header2", "description"),
    CollectionInfo("header3", "description"),
    CollectionInfo("header4", "description")
)

@Preview(showBackground = true)
@Composable
fun CollectionScreenPreview() {
    CollectionScreen(
        list = items
    )
}
