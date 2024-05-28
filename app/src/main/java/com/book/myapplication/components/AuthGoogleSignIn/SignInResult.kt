package com.book.myapplication.components.AuthGoogleSignIn

data class SignInResult(
    val data : SSOUserData?,
    val errorMessage : String?
)
data class SSOUserData(
    val userId: String,
    val username: String?,
    val profilePictureUrl: String?
)