package com.book.myapplication.api

import com.book.myapplication.model.Book
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "http://10.0.2.2:8080/book/"

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

interface IBook {
    @GET("getall")
    suspend fun getAllBooks(): List<Book>
}

val bookService = retrofit.create(IBook::class.java)