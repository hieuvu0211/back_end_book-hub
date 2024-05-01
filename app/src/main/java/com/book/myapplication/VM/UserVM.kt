package com.book.myapplication.VM

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.book.myapplication.model.User
import com.book.myapplication.model.UserLogin


class UserVM : ViewModel() {
    val data: MutableState<User?> = mutableStateOf(null)
    fun setData(data: User) {
        this.data.value = data
    }
}