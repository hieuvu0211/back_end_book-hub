package com.book.myapplication.api

import com.book.myapplication.model.Book
import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


data class ImageListResponse(
    @SerializedName("ListApiImage") val imageUrls: List<String>
)
private const val BASE_URL = "http://10.0.2.2:8080/book/"

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

interface IBook {
    @GET("getall")
    suspend fun getAllBooks(): List<Book>

    @GET("getbyid/{id}")
    suspend fun getBookById(@Path("id") id: String): Response<Book>

    @GET("image/{name}/{chapter}")
    suspend fun getListLinkImage(
        @Path("name") name: String,
        @Path("chapter") chapter: String
    ): Response<ImageListResponse>

    @GET("search/{name}")
    suspend fun searchBook(@Path("name") name :String) : Response<List<Book>>

    @GET("getFavorite")
    suspend fun getTopTenBook() : Response<List<Book>>
}

val bookService = retrofit.create(IBook::class.java)