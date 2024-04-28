package com.book.myapplication.VM

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.book.myapplication.api.ImageListResponse
import com.book.myapplication.api.bookService
import com.book.myapplication.model.Book
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class BookVM : ViewModel() {
    //create viewModel store data from back-end
    private val bookLists: MutableList<Book> = mutableListOf<Book>()

    private var _bookData = mutableStateOf<Book?>(null)
    val book_data : State<Book?> = _bookData


    private var _listLinks = mutableStateOf<ImageListResponse?>(null)
    val listLinks : State<ImageListResponse?> = _listLinks

    fun LoadListBooks(): MutableList<Book> {
        try {
            val result = runBlocking(Dispatchers.IO) {
                bookService.getAllBooks()
            }
            for (item in result) {
                bookLists.add(item)
            }
            return bookLists
        } catch (e: Error) {
            Log.i("resultAPI", "$e")
        }
        return bookLists
    }

    fun loadBookById(id : String) {
        viewModelScope.launch {
            try {
                val response = bookService.getBookById(id)
                if (response.isSuccessful) {
                    _bookData.value = response.body()
                }else{
                    Log.i("resultAPI", "failed data = ${response.message()}")
                }
            } catch (e: Exception) {
                Log.i("resultAPI", "Exception = $e")
            }
        }
    }

    fun loadListLinkApi(name: String, count: String) {
        viewModelScope.launch {
            try {
                val response = bookService.getListLinkImage(name, "chapter_$count")
                if(response.isSuccessful) {
                    _listLinks.value = response.body()
                }else {
                    Log.i("resultAPI", "failed data1 = ${response.message()}")
                }
            }catch (e : Exception) {
                Log.i("resultAPI", "Error = $e")
            }
        }
    }
}