package com.sapan.restjet.ui.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sapan.restjet.R
import com.sapan.restjet.data.Action
import com.sapan.restjet.data.ButtonColor
import com.sapan.restjet.data.ButtonContent
import com.sapan.restjet.data.ButtonProperty
import com.sapan.restjet.data.SpeedDial
import com.sapan.restjet.data.httpMethods
import com.sapan.restjet.ui.theme.delete_icon_button_radius
import com.sapan.restjet.ui.theme.radius_connected_button_group
import com.sapan.restjet.ui.theme.radius_connected_button_intermediate
import com.sapan.restjet.ui.theme.space_connected_button
import com.sapan.restjet.ui.theme.space_connected_button_icon_and_label


@Composable
fun FAB(
    onClick: () -> Unit,
    icon: ImageVector = Icons.Filled.Add,
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        onClick = onClick
    ) {
        Icon(
            imageVector = icon,
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

    LazyRow {
        items(buttons.size) { index ->
            val buttonContent = buttons[index]
            val shape = when {
                selectedId == index -> RoundedCornerShape(
                    topStart = radius_connected_button_group,
                    topEnd = radius_connected_button_group,
                    bottomStart = radius_connected_button_group,
                    bottomEnd = radius_connected_button_group
                )
                index == 0 -> RoundedCornerShape(
                    topStart = radius_connected_button_group,
                    bottomStart = radius_connected_button_group
                )
                index == buttons.size - 1 -> RoundedCornerShape(
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

            val itemClickHandler = {
                selectedId = index
                buttonContent.onClick()
            }

            ConnectedButton(
                onClick = itemClickHandler,
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
    dropDownItems: List<String> = emptyList(),
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

@Composable
fun SpeedDialFab(
    items: List<SpeedDial> = emptyList(),
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.wrapContentSize(),
        horizontalAlignment = Alignment.End
    ) {
        AnimatedVisibility(
            visible = expanded,
            enter = fadeIn(animationSpec = tween(durationMillis = 300)),
            exit = fadeOut(animationSpec = tween(durationMillis = 300))
        ) {
            Column(
                horizontalAlignment = Alignment.End
            ) {
                items.forEach { item ->
                    SmallFloatingActionButton(
                        onClick = {
                            expanded = false
                            item.onClick()
                        }
                    ) {
                        Text(
                            text = item.name,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }
                    Spacer(
                        modifier = Modifier.size(4.dp)
                    )
                }
            }
        }

        FAB(
            onClick = {
                expanded = !expanded
            },
            icon = if (expanded) Icons.Default.Close else Icons.Default.Add
        )

    }
}

@Composable
fun HttpMethodDropDown(
    onItemClick: (String) -> Unit = {},
    onDismissRequest: () -> Unit = {},
    items: List<String> = httpMethods,
    expanded: Boolean = true,
    modifier: Modifier = Modifier
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = modifier
    ) {
        items.forEach { httpMethod ->
            DropdownMenuItem(
                text = {
                    Text(text = httpMethod)
                },
                onClick = {
                    onItemClick(httpMethod)
                    onDismissRequest()
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HttpMethodDropDownPreview() {
    HttpMethodDropDown()
}

@Preview(showBackground = true)
@Composable
fun SpeedDialFabPreview() {
    SpeedDialFab()
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
        text = "click here",
        dropDownItems = httpMethods
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
            iconContentDescription = "button1",
            onClick = {}
        ),
        ButtonContent(
            action = Action.SELECT_REQUEST_TYPE,
            text = "Button 2",
            icon = Icons.Default.Add,
            iconContentDescription = "button2",
            onClick = {}
        ),
        ButtonContent(
            action = Action.ADD_HEADER,
            text = "Button 3",
            icon = Icons.Default.Add,
            iconContentDescription = "button3",
            onClick = {}
        ),
    )

    // Replace dimensionResource with dp for preview safety
    CompositionLocalProvider(
        LocalTextStyle provides MaterialTheme.typography.bodyMedium
    ) {
        ConnectedButtonGroup(
            buttons = buttons,
            modifier = Modifier.padding(8.dp),
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

