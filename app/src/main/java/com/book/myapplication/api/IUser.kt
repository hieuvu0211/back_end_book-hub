package com.book.myapplication.api

import com.book.myapplication.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
private const val BASE_URL = "http://10.0.2.2:8080/user/"

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

interface IUser {
    @GET("getall")
    suspend fun getallusers() : List<User>
}
val apiService = retrofit.create(IUser::class.java)

 suspend fun fetchUsers(): List<User> {
    return withContext(Dispatchers.IO) {
        apiService.getallusers()
    }
}