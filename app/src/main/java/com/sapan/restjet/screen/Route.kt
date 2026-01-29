package com.sapan.restjet.screen

import kotlinx.serialization.Serializable

@Serializable
sealed class Route(val route: String) {
    @Serializable
    data object Home: Route("Home")

    @Serializable
    data object Collection: Route("Collection")
}