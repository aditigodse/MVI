package com.example.mvidemo.presentation.intent

sealed class UIEvent {
    data class showToast(val message:String): UIEvent()
}