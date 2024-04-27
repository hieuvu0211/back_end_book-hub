package com.book.myapplication.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.book.myapplication.VM.BookVM
import com.book.myapplication.VM.UserVM
import com.book.myapplication.model.Book

fun handleNavigate(navController: NavController, data: MutableState<Book?>) {
    BookVM().setBookData(data)
    Log.i("resultAPI", "data = ${BookVM().getBookData().value}")
//    navController.navigate("about-book")
}
@Composable
fun ImageFromLocalhostUrl(navController: NavController, data: MutableState<Book?>) {
    val url : String = "http://10.0.2.2:8080/Books/${data.value?.book_name}/image.png"
    val painter = // You can customize image loading parameters here
        rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current).data(data = url)
                .apply(block = fun ImageRequest.Builder.() {
                    // You can customize image loading parameters here
                }).build()
        )

    Image(
        painter = painter,
        contentDescription = null,
        modifier = Modifier
            .size(120.dp)
            .clip(RoundedCornerShape(25.dp))
            .clickable { handleNavigate(navController, data) }
    )
}

@Composable
fun StoryCard(navController: NavController, storyName: String, data: MutableState<Book?>) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surface)
//            .padding(8.dp)
    ) {
        ImageFromLocalhostUrl(navController,data)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = if (storyName.length > 10) {
                "${storyName.take(10)}..."
            } else {
                storyName
            }, style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 8.dp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainUi(navController: NavController, data: UserVM) {
    val res = data.data.value // handle data receive
    var ListBooks = rememberSaveable {
        mutableListOf<Book>()
    }
    var bookData = rememberSaveable {
        mutableStateOf<Book?>(null)
    }

//    Scaffold(
//        topBar = {
//            // Add a top app bar
//            TopAppBar(
//                title = { Text(text = "My App") },
//                Modifier.background(color = Color.Green)
//            )
//        },
//        content = {
//            // Add content to the body of the screen
//            Column {
//                Text(text = "Content goes here")
//            }
//        },
//        bottomBar = {
//            // Add a bottom navigation bar
//            BottomAppBar(
//                containerColor = MaterialTheme.colorScheme.primaryContainer,
//                contentColor = MaterialTheme.colorScheme.primary,
//            ) {
//                Button(onClick = { /*TODO*/ }) {
//                    Text(text = "Home")
//
//                }
//            }
//        },
//        floatingActionButtonPosition = FabPosition.End,
//    )

    Column {
        Text(text = "This is main page")
        ListBooks = BookVM().LoadListBooks()

        LazyVerticalGrid(columns = GridCells.Fixed(4)) {
            items(ListBooks) { item ->
                bookData.value = item
                StoryCard(navController, item.book_name, bookData)
            }
        }
    }

}