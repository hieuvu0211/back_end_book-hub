package com.book.myapplication.components

import androidx.navigation.NavController
import androidx.versionedparcelable.ParcelField
import com.book.myapplication.ViewModel.LocalSettingsVM
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
}