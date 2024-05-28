package com.book.myapplication.components.AuthGoogleSignIn

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.book.myapplication.R
import com.book.myapplication.api.HandleError
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await

class GoogleSignInView(
    private val context: Context,
    private val oneTapClient: SignInClient
    ) {
    private val auth = Firebase.auth
    suspend fun signIn() : IntentSender? {
        val result = try {
            oneTapClient.beginSignIn(
                buildSignInRequest()
            ).await()
        }catch (e : Exception) {
            HandleError(e)
            return null
        }
        return result.pendingIntent.intentSender
    }
    suspend fun sinInWithIntent(intent : Intent) : SignInResult {
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val idToken = credential.googleIdToken
        val googleCredential = GoogleAuthProvider.getCredential(idToken, null)
        return try {
            val user = auth.signInWithCredential(googleCredential).await().user
            SignInResult(
                data = user?.run{
                    SSOUserData(
                        userId = uid,
                        username = displayName,
                        profilePictureUrl = photoUrl?.toString()
                    )
                },
                errorMessage = null
            )
        } catch (e : Exception) {
            HandleError(e)
            SignInResult(null, e.message)
        }
    }

    suspend fun signOut() {
        try {
            oneTapClient.signOut().await()
            auth.signOut()
        }catch (e : Exception) {
            HandleError(e)
        }
    }

    fun getSignInClient() : SSOUserData = auth.currentUser?.run {
        SSOUserData(
            userId = uid,
            username = displayName,
            profilePictureUrl = photoUrl?.toString()
        )
    } ?: SSOUserData("", null, null)
    private fun buildSignInRequest() : BeginSignInRequest {
        return BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(context.getString(R.string.web_client_id))
                    // Only show accounts previously used to sign in.
                    .setFilterByAuthorizedAccounts(true)
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }
}