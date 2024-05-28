package com.book.myapplication.model

data class UserSSOGoogle(
    val googleId: String,
    val email: String,
    val name: String,
    val picture: String
)