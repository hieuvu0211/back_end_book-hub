package com.book.myapplication.ViewModel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.book.myapplication.api.HandleError
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

    private var _listResultSearch : MutableList<Book> = mutableListOf<Book>()
    val listResultSearch : MutableList<Book> = _listResultSearch

    private var _listTop10Book : MutableList<Book> = mutableListOf<Book>()
    val listTop10Book : MutableList<Book> = _listTop10Book

    fun LoadListBooks(): MutableList<Book> {
        try {
            val result = runBlocking(Dispatchers.IO) {
                bookService.getAllBooks()
            }
            for (item in result) {
                bookLists.add(item)
            }
            return bookLists
        } catch (e: Exception) {
            HandleError(e)
        }
        return bookLists
    }

    fun loadBookById(id : String) {
        viewModelScope.launch {
            try {
                val response = bookService.getBookById(id)
                if (response.isSuccessful) {
                    _bookData.value = response.body()
                }
            } catch (e: Exception) {
                HandleError(e)
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
                HandleError(e)
            }
        }
    }

    fun loadSearchResult(name :String): MutableList<Book> {
        viewModelScope.launch {
            try {
                val res = bookService.searchBook(name)
                if(res.isSuccessful) {
                    _listResultSearch = res.body() as MutableList<Book>
                }
            }catch (e : Exception) {
                HandleError(e)
            }
        }
        return _listResultSearch
    }

    fun loadTopTenBook() : MutableList<Book> {
        viewModelScope.launch {
            try {
                val res = bookService.getTopTenBook()
                if(res.isSuccessful) {
                    _listTop10Book = res.body() as MutableList<Book>
                }
            }catch (e : Exception) {
                HandleError(e)
            }
        }
        return _listTop10Book
    }
}