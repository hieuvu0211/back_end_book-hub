package com.book.myapplication

import LoginForm
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
    LoginForm()
}


// Hiển thị từng item trong danh sách


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