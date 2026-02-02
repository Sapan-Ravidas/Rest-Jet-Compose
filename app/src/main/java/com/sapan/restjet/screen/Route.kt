package com.sapan.restjet.screen

import kotlinx.serialization.Serializable

@Serializable
sealed class Route(val route: String) {
    @Serializable
    data object Home: Route("Home")

    @Serializable
    data object Collection: Route("Collection")

    @Serializable
    data object Response: Route("Response")

    val title: String
        get() = when(this) {
            is Home -> "Rest Jet"
            is Collection -> "Collection"
            is Response -> "Response"
        }
}