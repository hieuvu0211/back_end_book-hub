package com.book.myapplication.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavController
import com.book.myapplication.VM.UserVM
import com.book.myapplication.api.bookService
import com.book.myapplication.model.Book

@Composable
fun MainUi(navController: NavController,data: UserVM) {
    val res = data.data.value
    var bookData = rememberSaveable {
        mutableListOf<Book>()
    }
    Column {
        Text(text = "This is Main UI")
        if (res != null) {
            Text(text = res.username.toString())
            Text(text = res.password.toString())
        }
        Button(onClick = {
            navController.navigate("login")
        }) {
            Text(text = "Back to Login")
        }
        LaunchedEffect(Unit) {
            try {
                val resultApi = bookService.getAllBooks()
                for(item in resultApi) {
                    bookData.add(item)
                }
            }catch (e :Error) {
                Log.i("resultAPI", "$e")
            }
        }
        LazyColumn {items(bookData) { item ->
            Row {
                Text(text = item.book_id)
                Text(text = item.book_name)
                Text(text = item.number_of_chapter.toString())
                Text(text = item.book_description)
            }
        }
        }
    }

}