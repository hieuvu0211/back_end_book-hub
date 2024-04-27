package com.book.myapplication.VM

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.book.myapplication.api.bookService
import com.book.myapplication.model.Book
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class BookVM : ViewModel() {
    //create viewModel store data from back-end
    private val bookLists: MutableList<Book> = mutableListOf<Book>()
    private var bookData = mutableStateOf<Book?>(null)

    //get with for loop

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

    fun setBookData(data: MutableState<Book?>) {
        bookData.value = data.value
    }

    fun getBookData(): MutableState<Book?> {
        return bookData
    }
}