package com.book.myapplication.api

import com.book.myapplication.model.User
import com.book.myapplication.model.UserLogin
import com.book.myapplication.model.UserSSORegister
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

private const val BASE_URL = "http://10.0.2.2:8080/user/"

private val retrofitUser = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

interface IUser {
    @GET("getuserbyid/{id}")
    suspend fun getUserById(@Path("id") id: String) : Response<User>

    @POST("login")
    suspend fun login(@Body res: UserLogin) : Response<User>

    @POST("register")
    suspend fun register(@Body res: UserLogin) : UserLogin

    @POST("ssoregister")
    suspend fun ssoRegister(@Body res : UserSSORegister) : Response<User>
}
val userService: IUser = retrofitUser.create(IUser::class.java)


