package com.book.myapplication.VM

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.book.myapplication.api.HandleError
import com.book.myapplication.api.ImageListResponse
import com.book.myapplication.api.favoriteService
import com.book.myapplication.model.Book
import com.book.myapplication.model.Favorite
import kotlinx.coroutines.launch

class FavoriteVM : ViewModel() {
    private val _isFollowLiveData = MutableLiveData<Boolean>()
    val isFollowLiveData: LiveData<Boolean> = _isFollowLiveData

    fun IsFollow(data : Favorite) : LiveData<Boolean> {


        viewModelScope.launch {
            try {
                val res = favoriteService.CheckFavorite(data)
                if(res.isSuccessful) {
                    Log.i("resultAPI", "final = ${res.body()}")
                    _isFollowLiveData.postValue(res.body() ?: false)
                }
            }catch (e: Exception) {
                HandleError(e)
                _isFollowLiveData.postValue(false)
            }
        }
        return _isFollowLiveData;
    }

    fun AddToFavorite(data : Favorite) : Boolean {
        var check = false
        viewModelScope.launch {
            try {
                val res = favoriteService.AddToFavorite(data)
                if(res.isSuccessful) {
                    Log.i("resultAPI", "data = ${res.body()}")
                    if(res.body()?.user_id != 0) {
                        check = true
                    }
                }
            }catch (e: Exception) {
                HandleError(e)
                check = false
            }
        }
        return check
    }
    fun DeleteFromFavorite(userid : String, bookid: String) : Boolean {
        Log.i("resultAPI", "check userid = $userid  and bookid = $bookid")
        var check = false
        viewModelScope.launch {
            try {
                val res = favoriteService.DeleteFromFavorite(userid, bookid)
                if(res.isSuccessful) {
                    Log.i("resultAPI", "data = ${res.body()}")
                    if(res.body() == true) {
                        check = true
                    }
                }
            }catch (e: Exception) {
                HandleError(e)
                check = false
            }
        }
        return check
    }
}