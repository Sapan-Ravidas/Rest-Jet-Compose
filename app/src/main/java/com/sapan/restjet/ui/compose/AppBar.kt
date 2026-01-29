package com.sapan.restjet.ui.compose

import android.widget.ImageButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sapan.restjet.R
import com.sapan.restjet.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    onNavigationClick: () -> Unit,
    onAvatarClick: () -> Unit,
    title: String = "Rest Jet",
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier.background(color = Color.Transparent),
        title = {
            Text(
                text = title,
                style = Typography.titleLarge,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onNavigationClick,
                modifier = Modifier.size(32.dp)
            ) {
                Icon(imageVector = Icons.Default.Menu,
                    contentDescription = null)
            }
        },
        actions = {
            Image(
                painter = painterResource(R.drawable.account_circle),
                contentDescription = "Account",
                modifier = Modifier
                    .size(32.dp)
                    .clickable{ onAvatarClick() }
            )
        },

    )
}

private val BOTTOM_NAVIGATION_COLLECTION = "Collection"
private val BOTTOM_NAVIGATION_HOME = "Home"

@Composable
fun BottomNavigationBar(
    selectedItem: String,
    onSelectedItem: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier.clip(RoundedCornerShape(50.dp))
    ) {
        NavigationBarItem(
            onClick = {
                onSelectedItem(BOTTOM_NAVIGATION_HOME)
            },
            selected = selectedItem == BOTTOM_NAVIGATION_HOME,
            icon = {
                Icon(imageVector = Icons.Default.Home, contentDescription = null)
            },
            label = {
                Text(text = BOTTOM_NAVIGATION_HOME)
            }
        )

        NavigationBarItem(
            onClick = {
                onSelectedItem(BOTTOM_NAVIGATION_COLLECTION)
            },
            selected = selectedItem == BOTTOM_NAVIGATION_COLLECTION,
            icon = {
                Icon(imageVector = Icons.Default.Star, contentDescription = null)
            },
            label = {
                Text(text = BOTTOM_NAVIGATION_COLLECTION)
            }
        )
    }
}


@Preview
@Composable
fun NavigationBarPreview() {
    BottomNavigationBar(
        selectedItem = BOTTOM_NAVIGATION_HOME,
        onSelectedItem = {}
    )
}

@Preview(showBackground = true)
@Composable
fun AppBarPreview() {
    AppBar(
        onNavigationClick = {},
        onAvatarClick = {},
        modifier = Modifier.padding(start = 8.dp, end = 8.dp)
    )
}