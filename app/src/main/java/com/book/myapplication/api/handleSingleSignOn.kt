package com.book.myapplication.api

import com.book.myapplication.model.User
import com.book.myapplication.model.UserSSOGoogle
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

private val retrofitSSO = Retrofit.Builder()
    .baseUrl("http://10.0.2.2:8080/auth/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()
interface LoginSSO {
    @POST("google")
    suspend fun googleLogin(@Body idToken: String): Response<UserSSOGoogle>
}

val loginSSOService: LoginSSO = retrofitSSO.create(LoginSSO::class.java)