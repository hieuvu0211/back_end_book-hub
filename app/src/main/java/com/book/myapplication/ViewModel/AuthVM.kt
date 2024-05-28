package com.book.myapplication.ViewModel

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.book.myapplication.GlobalState.UserData
import com.book.myapplication.R
import com.book.myapplication.api.HandleError
import com.book.myapplication.api.userService
import com.book.myapplication.model.User
import com.book.myapplication.model.UserSSORegister
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthVM(application: Application) : AndroidViewModel(application) {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val googleSignInClient: GoogleSignInClient
    private val dataStoreUser = UserData(application)

    private val _user = MutableLiveData<FirebaseUser?>()
    val user: LiveData<FirebaseUser?> = _user

    private val _signInIntent = MutableLiveData<Intent?>()
    val signInIntent: LiveData<Intent?> = _signInIntent

    init {
        _user.value = firebaseAuth.currentUser

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(application.getString(R.string.web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(application, gso)
    }

    fun signIn() {
        _signInIntent.value = googleSignInClient.signInIntent
    }

    fun onSignInIntentHandled() {
        _signInIntent.value = null
    }

    fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            if (account != null) {
                firebaseAuthWithGoogle(account.idToken!!)
            }
        } catch (e: ApiException) {
            // Handle error
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        viewModelScope.launch {
            try {
                firebaseAuth.signInWithCredential(credential).await()
                _user.value = firebaseAuth.currentUser

                val currentUser = firebaseAuth.currentUser
                currentUser?.let {
                    Log.i("resultAPI", "info user = ${it.uid} ${it.displayName} ${it.email} ${it.photoUrl}")
                    val res = userService.ssoRegister(UserSSORegister(it.uid, it.displayName ?: it.email ?: "unknown",
                        (it.photoUrl ?: "").toString()
                    ))
                    if(res.isSuccessful) {
                        dataStoreUser.setDataUserInLocal(res.body() as User)
                    }
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun signOut() {
        firebaseAuth.signOut()
        googleSignInClient.signOut().addOnCompleteListener {
            _user.value = null
        }
    }
}