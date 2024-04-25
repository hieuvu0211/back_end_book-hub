package com.book.myapplication.VM

import androidx.lifecycle.ViewModel
import com.book.myapplication.model.Book

class BookVM : ViewModel() {
    //create viewModel store data from back-end
    val bookData: MutableList<Book> = mutableListOf<Book>()
    //get with for loop
    fun setData(data: Book) {
        bookData.add(data)
    }
}