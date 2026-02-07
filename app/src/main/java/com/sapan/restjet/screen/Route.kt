package com.sapan.restjet.screen

import androidx.navigation.navArgument
import kotlinx.serialization.Serializable

@Serializable
sealed class Route(val route: String) {
    @Serializable
    data class Home(
        override val title: String = "Rest Jet"
    ): Route("Home/{title}") {
        companion object {
            const val routeWithArgs = "Home/{title}"
            val arguments = listOf(
                navArgument("title") {
                    defaultValue = "Rest Jet"
                }
            )
        }
    }

    @Serializable
    data object Collection: Route("Collection")

    @Serializable
    data object Response: Route("Response")

    open val title: String
        get() = when(this) {
            is Home -> this.title
            is Collection -> "Collection"
            is Response -> "Response"
        }
}