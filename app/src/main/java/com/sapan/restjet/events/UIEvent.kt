package com.sapan.restjet.events

sealed class UIEvent {
    data object NavigateToResponseScreen: UIEvent()
}