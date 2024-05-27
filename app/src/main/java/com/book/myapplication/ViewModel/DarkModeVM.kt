package com.book.myapplication.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.book.myapplication.GlobalState.DarkModeData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DarkModeVM(private val darkModeData : DarkModeData) : ViewModel() {
    private val _isDarkTheme = MutableStateFlow<Boolean>(false)
    val isDarkTheme = _isDarkTheme.asStateFlow()

    private val _isFollowSystemTheme = MutableStateFlow<Boolean>(false)
    val isFollowSystemTheme = _isFollowSystemTheme.asStateFlow()

    init {
        viewModelScope.launch {
            darkModeData.getDarkModeData.collect { isDarkTheme ->
                _isDarkTheme.value = isDarkTheme == "1"
            }
        }
    }
    fun changeDarkTheme(isDark: Boolean) {
        viewModelScope.launch {
            val mode = if (isDark) "1" else "0"
            darkModeData.setDarkModeData(mode)
            _isDarkTheme.value = isDark
        }
    }
    fun changeFollowSystemTheme(isFollow: Boolean) {
        _isFollowSystemTheme.value = isFollow
    }
}