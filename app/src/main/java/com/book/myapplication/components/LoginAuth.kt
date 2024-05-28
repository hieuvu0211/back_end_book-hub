package com.book.myapplication.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.book.myapplication.ViewModel.AuthVM
import com.google.firebase.auth.FirebaseUser

@SuppressLint("SuspiciousIndentation")
@Composable
fun LoginAuth(authViewModel : AuthVM) {
    val user by authViewModel.user.observeAsState()
        if (user == null) {
            GoogleSignInButton { authViewModel.signIn() }
        } else {
            UserInfo(user!!, onLogoutClick = { authViewModel.signOut() })
        }

}
@Composable
fun GoogleSignInButton(onClick: () -> Unit) {
    Button(onClick = onClick, modifier = Modifier.padding(16.dp)) {
        Text("Sign in with Google")
    }
}

@Composable
fun UserInfo(user: FirebaseUser, onLogoutClick: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Welcome, ${user.displayName}")
        Text("Email: ${user.email}")
        // Load image using your preferred image loading library

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onLogoutClick) {
            Text("Logout")
        }
    }
}