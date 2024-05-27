package com.book.myapplication.components

import kotlinx.serialization.Serializable

@Serializable
sealed class ScreenView {
    @Serializable
    data class SettingView(
        val id  : String?
    ) : ScreenView()

    @Serializable
    data class LanguageView(
        val change : String?
    ) : ScreenView()

    @Serializable
    data object DarkModeView : ScreenView()

}