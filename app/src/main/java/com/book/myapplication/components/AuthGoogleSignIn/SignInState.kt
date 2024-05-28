package com.book.myapplication.components.AuthGoogleSignIn

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)
