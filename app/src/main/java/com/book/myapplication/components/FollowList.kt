package com.book.myapplication.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.book.myapplication.VM.BookVM
import com.book.myapplication.api.HandleError
import com.book.myapplication.api.favoriteService
import com.book.myapplication.model.Book

@Composable
fun FollowList(idUser : String) {
    val book_vm : BookVM = viewModel()
    val navController = rememberNavController()
    val listFollow = rememberSaveable {
        mutableListOf<Book>()
    }
    var isDataLoaded by rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(true) {
        try {
            val res = favoriteService.GetDataByUserId(idUser)
            if(res.isSuccessful) {
                res.body()?.forEach { item ->
                    Log.i("resultAPI", "item = ${item}")
                    listFollow.add(item)
                }
                isDataLoaded = true
            }
        }catch (e : Exception) {
            HandleError(e)
        }
    }
    Text(text = "Follow List")
    if(isDataLoaded) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            BookList(listFollow, book_vm = book_vm) {book ->
                navController.navigate("about-book/${book.book_id}")
            }
        }
    }


}

@Preview(showBackground = true)
@Composable
fun FollowListPreview() {
    FollowList("1")
}