package com.book.myapplication.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.book.myapplication.api.HandleError
import com.book.myapplication.api.userService
import com.book.myapplication.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class UserVM : ViewModel() {
    val data: MutableStateFlow<User?> = MutableStateFlow(null)
    private var _userInfo: MutableStateFlow<User> = MutableStateFlow(User(0,"",""))
    val userInfo: StateFlow<User> = _userInfo

    fun setData(data: User) {
        this.data.value = data
    }

    fun loadUserInfo(id : String) {
        viewModelScope.launch {
            try {
                val res = userService.getUserById(id)
                if(res.isSuccessful) {
                    _userInfo.value = res.body()!!
                }else {
                    HandleError(Exception("Error: ${res.message()}"))
                }
            }catch (e : Exception){
                HandleError(e)
            }
        }
    }

}