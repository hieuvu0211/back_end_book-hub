package com.book.myapplication.model

data class UserLogin(
    val username: String,
    val password: String
)
data class User(
    val user_id : Int,
    val username: String,
    val password: String,
    val uid : String?,
    val imgurl : String?
)

data class UserSSORegister(
    val uid: String,
    val username: String,
    val imgurl: String?
)


