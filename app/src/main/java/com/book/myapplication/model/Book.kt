package com.book.myapplication.model

data class Book(
    val book_id: String,
    val book_name: String,
    val number_of_chapter: Int,
    val number_of_likes: Int,
    val book_description: String
)
