package com.sapan.restjet.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sapan.restjet.R
import com.sapan.restjet.ui.theme.delete_icon_button_radius
import com.sapan.restjet.ui.theme.radius_connected_button_group
import com.sapan.restjet.ui.theme.radius_connected_button_intermediate
import com.sapan.restjet.ui.theme.space_connected_button
import com.sapan.restjet.ui.theme.space_connected_button_icon_and_label

data class ButtonContent(
    val action: Action,
    val text: String,
    val icon: ImageVector,
    val iconContentDescription: String
)

enum class Action(string: String) {
    ADD_HEADER("ADD_HEADER"),
    ADD_QUERY_PARAM("ADD_QUERY_PARAM"),
    SELECT_REQUEST_TYPE("SELECT_REQUEST_TYPE")
}

data class ButtonProperty(
    val shape: Shape,
    val buttonColor: ButtonColor
)

data class ButtonColor(
    val color: Color,
    val contentColor: Color
)

@Composable
fun FAB(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        onClick = {}
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(R.string.fab__add_content_description)
        )
    }
}

@Composable
fun ButtonPrimary(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier
) {
    ElevatedButton(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
    ) {
        Text(
            text = text
        )
    }
}

@Composable
fun ButtonSecondary(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick
    ) {
        Text(
            text = text
        )
    }
}

@Composable
fun ButtonDefault(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier
) {
    ElevatedButton(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.surfaceContainerLow),
        modifier = modifier
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun DeleteIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier.background(
            color = MaterialTheme.colorScheme.secondaryContainer,
            shape = RoundedCornerShape(delete_icon_button_radius)
        )
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "delete",
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DeleteIconPreview() {
    DeleteIconButton(onClick = {})
}

@Composable
fun ConnectedButtonGroup(
    buttons: List<ButtonContent> = listOf(),
    modifier: Modifier = Modifier
) {
    var selectedId by remember { mutableStateOf(-1) }
    val colorScheme = MaterialTheme.colorScheme

    LazyRow(

    ) {
        items(buttons.size) { index ->
            val buttonContent = buttons[index]
            val shape = when(index) {
                0 -> RoundedCornerShape(
                    topStart = radius_connected_button_group,
                    bottomStart = radius_connected_button_group
                )
                buttons.size - 1 -> RoundedCornerShape(
                    topEnd = radius_connected_button_group,
                    bottomEnd = radius_connected_button_group
                )
                else -> RoundedCornerShape(radius_connected_button_intermediate)
            }

            val buttonColor = when {
                index == selectedId -> ButtonColor(
                    color = colorScheme.secondary,
                    contentColor = colorScheme.onSecondary
                )
                else -> ButtonColor(
                    color = colorScheme.secondaryContainer,
                    contentColor = colorScheme.onSecondaryContainer
                )
            }

            val buttonProperty = ButtonProperty(
                shape = shape,
                buttonColor = buttonColor,
            )

            ConnectedButton(
                onClick = { selectedId = index },
                text = buttonContent.text,
                shapes = shape,
                iconVector = buttonContent.icon,
                iconContentDescription = buttonContent.iconContentDescription,
                color = buttonColor.color,
                contentColor = buttonColor.contentColor,
                colorScheme = colorScheme
            )

            if (index < buttons.size - 1) {
                Spacer(
                    modifier = Modifier.width(space_connected_button)
                )
            }
        }
    }
}

@Composable
fun ConnectedButton(
    onClick: () -> Unit,
    text: String,
    iconVector: ImageVector = Icons.Default.Add,
    iconContentDescription: String = stringResource(R.string.fab__add_content_description),
    colorScheme: ColorScheme = MaterialTheme.colorScheme,
    shapes: Shape = ButtonDefaults.shape,
    color: Color = colorScheme.secondaryContainer,
    contentColor: Color = colorScheme.onSecondaryContainer,
    modifier: Modifier = Modifier
) {
    ElevatedButton(
        modifier = modifier,
        shape = shapes,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(color)
    ) {
        Icon(
            imageVector = iconVector,
            contentDescription = iconContentDescription,
            tint = contentColor
        )
        Spacer(
            modifier = Modifier.width(space_connected_button_icon_and_label)
        )
        Text(
            text = text,
            color = contentColor
        )
    }
}

@Composable
fun ExpandButton(
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick
    ) {
        Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null)
    }
}

@Composable
fun ShrinkButton(
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick
    ) {
        Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = null)
    }
}

@Preview(showBackground = true)
@Composable
fun ExpandButtonPreview() {
    ExpandButton({})
}

@Preview(showBackground = true)
@Composable
fun ShrinkButtonPreview() {
    ShrinkButton {  }
}

@Preview(showBackground = true)
@Composable
fun ConnectedButtonPreview() {
    ConnectedButton(
        onClick = {},
        text = "click here"
    )
}

@Preview(showBackground = true)
@Composable
fun ConnectedButtonGroupPreview() {
    val buttons = listOf(
        ButtonContent(
            action = Action.ADD_QUERY_PARAM,
            text = "Button 1",
            icon = Icons.Default.Add,
            iconContentDescription = "button1"
        ),
        ButtonContent(
            action = Action.SELECT_REQUEST_TYPE,
            text = "Button 2",
            icon = Icons.Default.Add,
            iconContentDescription = "button2"
        ),
        ButtonContent(
            action = Action.ADD_HEADER,
            text = "Button 3",
            icon = Icons.Default.Add,
            iconContentDescription = "button3"
        ),
    )

    // Replace dimensionResource with dp for preview safety
    CompositionLocalProvider(
        LocalTextStyle provides MaterialTheme.typography.bodyMedium
    ) {
        ConnectedButtonGroup(
            buttons = buttons,
            modifier = Modifier.padding(8.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ButtonSecondaryPreview() {
    ButtonSecondary(
        onClick = {},
        text = "click here"
    )
}

@Preview(showBackground = true)
@Composable
fun FABPreview() {
    FAB(
        onClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun ButtonPrimaryPreview() {
    ButtonPrimary(
        onClick = {},
        text = "Button"
    )
}

@Preview(showBackground = true)
@Composable
fun ButtonDefaultPreview() {
    ButtonDefault(
        onClick = {},
        text = "Button"
    )
}

