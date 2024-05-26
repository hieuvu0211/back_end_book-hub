package com.book.myapplication.model

import kotlinx.coroutines.flow.MutableStateFlow
import java.sql.Date

data class History(
    var user_id : Int,
    val book_id : Int,
    val book_name: String,
    var start_date : String,
    var last_read_page: Int,
    var status : String
)
