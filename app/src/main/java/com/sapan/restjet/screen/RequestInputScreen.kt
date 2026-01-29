package com.sapan.restjet.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sapan.restjet.ui.compose.Action
import com.sapan.restjet.ui.compose.ButtonContent
import com.sapan.restjet.ui.compose.ButtonDefault
import com.sapan.restjet.ui.compose.ConnectedButtonGroup
import com.sapan.restjet.ui.compose.TextInputField
import com.sapan.restjet.ui.theme.Typography

val buttons = listOf(
    ButtonContent(
        action = Action.ADD_QUERY_PARAM,
        text = "Action",
        icon = Icons.Default.ArrowDropDown,
        iconContentDescription = "button1"
    ),
    ButtonContent(
        action = Action.SELECT_REQUEST_TYPE,
        text = "Header",
        icon = Icons.Default.Add,
        iconContentDescription = "button2"
    ),
    ButtonContent(
        action = Action.ADD_HEADER,
        text = "Params",
        icon = Icons.Default.Add,
        iconContentDescription = "button3"
    ),
)

@Composable
fun RequestInputScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.surfaceContainerLow.copy(alpha = 0.8f),
                shape = RoundedCornerShape(12.dp)
            )
            .wrapContentSize()

    ) {
        Column(
            modifier = modifier
                        .wrapContentSize(Alignment.Center)
                ,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "REQUEST",
                color = Color.Black.copy(alpha = 0.8f),
                style = Typography.titleSmall
            )

            TextInputField(
                label = "BASE_URL",
                onValueChange = {},
            )

            TextInputField(
                label = "PATH",
                onValueChange = {}
            )

            TextInputField(
                label = "REQUEST_BODY",
                onValueChange = {},
                modifier = Modifier
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(color = MaterialTheme.colorScheme.surfaceContainerLow.copy(alpha = 0.5f))
            )

            ConnectedButtonGroup(
                buttons = buttons,
            )

            ButtonDefault(
                onClick = {},
                text = "SEND"
            )

        }
    }
}

@Preview
@Composable
fun RequestInputScreenPreview() {
    RequestInputScreen()
}

