package com.book.myapplication.ViewModel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.book.myapplication.model.History
import kotlinx.coroutines.launch
import com.book.myapplication.api.HandleError
import com.book.myapplication.api.historyService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.format.DateTimeFormatter
import java.time.ZoneOffset
import java.time.ZonedDateTime

class HistoryVM : ViewModel() {
    private var _listHistory : MutableStateFlow<List<History>> = MutableStateFlow<List<History>>(
        emptyList()
    )
    val listHistory : MutableStateFlow<List<History>> = _listHistory

    private var _listTopTenHistory :MutableStateFlow<List<History>> = MutableStateFlow<List<History>>(
        emptyList()
    )
    val listTopTenHistory :MutableStateFlow<List<History>> = _listTopTenHistory

    private var _lastChapterRead = MutableStateFlow<Int?>(0)
    val lastChapterRead = _lastChapterRead.asStateFlow()

    fun loadListHistory(id : String) {
        viewModelScope.launch {
            try {
                val res = historyService.getHistoryByUserId(id)
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
                val res = historyService.getTopTenHistoryByUserId(id)
                if(res.isSuccessful) {
                    _listTopTenHistory.value = res.body() as MutableList<History>
                }
            }catch (e : Exception) {
                HandleError(e)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurrentDateTimeInISO8601(): String {
        val currentDateTime = ZonedDateTime.now(ZoneOffset.UTC)
        val formatter = DateTimeFormatter.ISO_INSTANT
        return currentDateTime.format(formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateHistory(data : History) {
        viewModelScope.launch {
            try {
                val formatDateTime = getCurrentDateTimeInISO8601()
                data.start_date = formatDateTime
                val res = historyService.updateHistory(data)
                if(res.isSuccessful) {
                    Log.i("resultAPI", "update history is success")
                }
            }catch (e : Exception) {
                HandleError(e)
            }
        }
    }

    fun checkIsRead(listId : String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val res = historyService.checkIsRead(listId)
                if(res.isSuccessful) {
                    res.body()?. let {
                        _lastChapterRead.value = it
                    }
                }
            }catch(e : Exception) {
                HandleError(e)
            }
        }
    }
}