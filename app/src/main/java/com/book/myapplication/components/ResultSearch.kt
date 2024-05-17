package com.book.myapplication.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.book.myapplication.ViewModel.BookVM
import com.book.myapplication.model.Book

@Composable
fun ResultSearch(navController: NavController,name : String?) {
    val book_vm : BookVM = viewModel()
    var data = rememberSaveable {
        mutableListOf<Book>()
    }
    
    if(name != null) {
        Column(modifier = Modifier.fillMaxSize().padding(12.dp)) {
            Text(text = "Result for: $name")
            data = book_vm.loadSearchResult(name)

            LazyVerticalGrid(columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(12.dp)
            ) {
                items(data) {item ->
                    StoryCard(book = item) {book ->
                        navController.navigate("about-book/${item.book_id}")
                    }
                }
            }
        }

    }

}