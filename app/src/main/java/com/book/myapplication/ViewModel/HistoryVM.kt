package com.book.myapplication.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.book.myapplication.model.History
import kotlinx.coroutines.launch
import com.book.myapplication.api.HandleError
import com.book.myapplication.api.historyService
import kotlinx.coroutines.flow.MutableStateFlow

class HistoryVM : ViewModel() {
    private var _listHistory : MutableStateFlow<List<History>> = MutableStateFlow<List<History>>(
        emptyList()
    )
    val listHistory : MutableStateFlow<List<History>> = _listHistory

    private var _listTopTenHistory :MutableStateFlow<List<History>> = MutableStateFlow<List<History>>(
        emptyList()
    )
    val listTopTenHistory :MutableStateFlow<List<History>> = _listTopTenHistory

    fun loadListHistory(id : String) {
        viewModelScope.launch {
            try {
                val res = historyService.GetHistoryByUserId(id)
                if(res.isSuccessful) {
                    _listHistory.value = res.body() as MutableList<History>
                }
            }catch (e : Exception) {
                HandleError(e)
            }
        }

    }

    fun loadTopTenHistory(id : String) {
        viewModelScope.launch {
            try {
                val res = historyService.GetTopTenHistoryByUserId(id)
                if(res.isSuccessful) {
                    _listTopTenHistory.value = res.body() as MutableList<History>
                }
            }catch (e : Exception) {
                HandleError(e)
            }
        }

    }
}