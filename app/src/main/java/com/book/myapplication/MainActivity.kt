package com.book.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.book.myapplication.api.apiService
import com.book.myapplication.api.fetchUsers
import com.book.myapplication.model.User
import com.book.myapplication.ui.theme.MyappTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyappTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GreetingPreview()
                }
            }
        }
    }
}


@Composable
fun UserListScreen() {
    var users by remember { mutableStateOf<List<User>>(emptyList()) }

        LaunchedEffect(Unit) {
            try {
                users = fetchUsers()
            } catch (e: Exception) {
                Log.i("eAPI", "Failed $e")
            }
        }

    LazyColumn {
        items(users) { user ->
            Text(text = "${user.username} - ${user.password}")
        }
    }
}


// Hiển thị từng item trong danh sách
@Composable
fun UserItem(user: User) {
    Text("${user.username} - ${user.password} ")
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyappTheme {
        Column(modifier = Modifier.fillMaxWidth()) {
//            LoginForm()
            UserListScreen()
        }

    }
}