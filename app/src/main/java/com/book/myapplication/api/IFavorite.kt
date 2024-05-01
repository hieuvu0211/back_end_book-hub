package com.book.myapplication.api

import com.book.myapplication.model.Book
import com.book.myapplication.model.Favorite
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "http://10.0.2.2:8080/favorite/"

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

interface IFavorite {
    @GET("getbyuserid/{id}")
    suspend fun GetDataByUserId(@Path("id") id :String) : Response<List<Book>>
}

val favoriteService : IFavorite = retrofit.create(IFavorite::class.java)