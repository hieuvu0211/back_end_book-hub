package com.book.myapplication.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LocalSettingsVM : ViewModel() {
    private val _language = MutableStateFlow("en")
    val language: StateFlow<String> = _language

    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme

    fun changeLanguage(newLanguage: String) {
        viewModelScope.launch {
            _language.emit(newLanguage)
        }
    }

    fun toggleTheme() {
        viewModelScope.launch {
            _isDarkTheme.emit(!_isDarkTheme.value)
        }
    }
}