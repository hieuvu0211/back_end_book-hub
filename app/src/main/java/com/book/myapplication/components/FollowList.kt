package com.book.myapplication.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.book.myapplication.VM.BookVM
import com.book.myapplication.VM.FavoriteVM
import com.book.myapplication.api.HandleError
import com.book.myapplication.api.favoriteService
import com.book.myapplication.model.Book

@Composable
fun CardStoryFollow(
    book: Book,
    onBookClick: (Book) -> Unit,
    userid: String,
    modifier: Modifier = Modifier
) {
    val favorite_vm : FavoriteVM = viewModel()
    Row(

    ) {
        ImageFromLocalhostUrl(book, onBookClick)
        Column {
            Text(text = book.book_name, fontSize = 20.sp, fontWeight = FontWeight(600))
            Text(text = "chapter : ${book.number_of_chapter}")
            Button(onClick = {
                favorite_vm.DeleteFromFavorite("${userid}-${book.book_id}")
                Log.i("resultAPI", "result = ${book.book_id}, userid = ${userid}")
            }) {
                Text(text = "Unfollow", color = Color.Red)
            }
        }
    }
}

@Composable
fun ListFollow(
    list_books: List<Book>,
    userid: String,
    onBookClick: (Book) -> Unit,
) {
    LazyColumn {
        items(list_books) {item ->
            CardStoryFollow(item, onBookClick, userid)
            Spacer(modifier = Modifier.height(15.dp))
        }
    }
}

@Composable
fun FollowList(navController: NavController,idUser: String) {
    val book_vm: BookVM = viewModel()
    val listFollow = remember {
        mutableListOf<Book>()
    }
    var isDataLoaded by rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(true) {
        try {
            val res = favoriteService.GetDataByUserId(idUser)
            if (res.isSuccessful) {
                res.body()?.forEach { item ->
                    listFollow.add(item)
                }
                isDataLoaded = true
            }
        } catch (e: Exception) {
            HandleError(e)
        }
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Icon(
            Icons.Filled.ArrowBack, "backToMain",
            modifier = Modifier
                .size(32.dp)
                .clickable {
                    navController.navigate("main")
                }
        )
        if (isDataLoaded) {
            Text(text = "Follow List")
            ListFollow(listFollow, idUser) {book ->
                navController.navigate("about-book/${book.book_id}")
            }
        }

    }


}

@Preview(showBackground = true)
@Composable
fun FollowListPreview() {
    val navController = rememberNavController()
    FollowList(navController,"1")
}