package com.book.myapplication.api

import com.book.myapplication.model.History
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

private const val BASE_URL = "http://10.0.2.2:8080/history/"
private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()
interface IHistory {
    @GET("getbyuserid/{id}")
    suspend fun getHistoryByUserId(@Path("id") id: String): Response<List<History>>

    @GET("gettoptenbyuserid/{id}")
    suspend fun getTopTenHistoryByUserId(@Path("id") id: String): Response<List<History>>

    @PUT("updateHistory")
    suspend fun updateHistory(@Body() data : History) : Response<Boolean>
}

val historyService: IHistory = retrofit.create(IHistory::class.java)