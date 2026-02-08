package com.sapan.restjet.ui.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

enum class ScreenSize {
    SMALL,
    MEDIUM,
    LARGE
}

@Composable
fun getScreenSize(): ScreenSize {
    val configuration = LocalConfiguration.current
    return when {
        configuration.screenWidthDp >= 960 -> ScreenSize.LARGE
        configuration.screenWidthDp >= 600 -> ScreenSize.MEDIUM
        else -> ScreenSize.SMALL
    }
}