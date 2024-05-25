package com.book.myapplication.ViewModel

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.book.myapplication.GlobalState.LanguageData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Locale

class LanguageVM(private val languageData: LanguageData) : ViewModel() {
    private val _locale = MutableStateFlow(Locale.getDefault())
    val locale: StateFlow<Locale> = _locale

    init {
        viewModelScope.launch {
            languageData.getDataUserFromLocal.collect { newLocale ->
                _locale.value = newLocale
            }
        }
    }
    fun updateLocale(newLocale: Locale) {
        viewModelScope.launch {
            languageData.SetDataUserInLocal(newLocale)
            _locale.value = newLocale
        }
    }
}