package com.book.myapplication.model

import java.sql.Date

data class History(
    val user_id : Int,
    val book_id : Int,
    val book_name: String,
    val start_date : String,
    val last_read_page: Int,
    val status : String
)
