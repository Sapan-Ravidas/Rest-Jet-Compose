package com.sapan.restjet.data

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
    val onClick: () -> Unit,
    val isDropDown: Boolean = false,
    val dropDownItems: List<String> = emptyList(),
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

val httpMethods = listOf(
    "GET", "POST", "PUT", "DELETE", "PATCH", "HEAD", "OPTIONS"
)
