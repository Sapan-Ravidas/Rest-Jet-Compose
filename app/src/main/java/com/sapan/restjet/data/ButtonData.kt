package com.sapan.restjet.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector

data class SpeedDial(
    val name: String,
    val onClick: () -> Unit
)

data class ButtonContent(
    val action: Action,
    val text: String,
    val icon: ImageVector,
    val iconContentDescription: String,
    val isDropDown: Boolean = false,
    val dropDownItems: List<String> = emptyList()
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

val fabSpeedDialItems = listOf<SpeedDial>(
    SpeedDial("+ Request") {},
    SpeedDial("+ Collection") {}
)

val httpMethods = listOf(
    "GET", "POST", "PUT", "DELETE", "PATCH", "HEAD", "OPTIONS"
)

val buttons = listOf(
    ButtonContent(
        action = Action.ADD_QUERY_PARAM,
        text = "GET",
        icon = Icons.Default.ArrowDropDown,
        iconContentDescription = "select https method",
        dropDownItems = httpMethods
    ),
    ButtonContent(
        action = Action.SELECT_REQUEST_TYPE,
        text = "Header",
        icon = Icons.Default.Add,
        iconContentDescription = "add headers"
    ),
    ButtonContent(
        action = Action.ADD_HEADER,
        text = "Params",
        icon = Icons.Default.Add,
        iconContentDescription = "add query parameters"
    ),
)
