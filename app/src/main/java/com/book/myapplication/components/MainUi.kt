package com.book.myapplication.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.book.myapplication.VM.BookVM
import com.book.myapplication.VM.UserVM
import com.book.myapplication.model.Book


@Composable
fun ImageFromLocalhostUrl(
    book : Book,
    onBookClick: (Book) -> Unit,
) {
    val url: String = "http://10.0.2.2:8080/Books/${book.book_name}/image.png"
    val painter =
        rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current).data(data = url)
                .apply(block = fun ImageRequest.Builder.() {
                    // You can customize image loading parameters here
                }).build()
        )
    Box(modifier = Modifier.fillMaxWidth().clickable { onBookClick(book) }) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(25.dp))
        )
    }

}

@Composable
fun StoryCard(
    book : Book,
    onBookClick: (Book) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surface)

//            .padding(8.dp)
    ) {
        ImageFromLocalhostUrl(book, onBookClick)
        Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = if (book.book_name.length > 10) {
                    "${book.book_name.take(10)}..."
                } else {
                    book.book_name
                }, style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 8.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )


    }
}

//render List Book data
@Composable
fun BookList(book_vm : BookVM, onBookClick: (Book) -> Unit) {
    Column {
        Text(text = "This is main page")
        var ListBooks = (book_vm.LoadListBooks() ?: emptyList()).toMutableList()

        LazyVerticalGrid(columns = GridCells.Fixed(4)) {
            items(ListBooks) { item ->
                StoryCard(item, onBookClick)
            }
        }
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
    val book_vm = viewModel<BookVM>()
    BookList(book_vm = book_vm) {book ->
        navController.navigate("about-book/${book.book_id}")
    }

}