package com.book.myapplication.api

import com.book.myapplication.model.Book
import com.book.myapplication.model.Favorite
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

private const val BASE_URL = "http://10.0.2.2:8080/favorite/"

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

interface IFavorite {
    @GET("getbyuserid/{id}")
    suspend fun GetDataByUserId(@Path("id") id: String): Response<List<Book>>

    @POST("checkfavorite")
    suspend fun CheckFavorite(@Body data: Favorite): Response<Boolean>

    @POST("addfavorite")
    suspend fun AddToFavorite(@Body data: Favorite): Response<Favorite>

    @DELETE("deletefavorite/{userid}/{bookid}")
    suspend fun DeleteFromFavorite(
        @Path("userid") userid: String,
        @Path("bookid") bookid: String
    ): Response<Boolean>
}

val favoriteService: IFavorite = retrofit.create(IFavorite::class.java)