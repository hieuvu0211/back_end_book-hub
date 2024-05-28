package com.book.myapplication.api

import com.book.myapplication.model.Book
import com.book.myapplication.model.Favorite1
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

private const val BASE_URL = "http://10.0.2.2:8080/favorite/"

private val retrofitFavorite = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

interface IFavorite {
    @GET("getbyuserid/{id}")
    suspend fun GetDataByUserId(@Path("id") id: String): Response<List<Book>>

    @GET("checkfavorite/{id}")
    suspend fun CheckFavorite(@Path("id") id: String,): Response<Boolean>

    @POST("addfavorite")
    suspend fun AddToFavorite(@Body data: Favorite1): Response<Favorite1>

    @DELETE("deletefavorite/{id}")
    suspend fun DeleteFromFavorite(
        @Path("id") id: String,
    ): Response<Boolean>
}

val favoriteService: IFavorite = retrofitFavorite.create(IFavorite::class.java)