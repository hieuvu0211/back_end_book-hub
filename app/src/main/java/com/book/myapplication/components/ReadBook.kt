package com.book.myapplication.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.book.myapplication.VM.BookVM


@Composable
fun ConfigImage(url : String) {
    val painter =
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
            .fillMaxWidth()
            .aspectRatio(0.5f)
    )
}

@Composable
fun ReadBook(bookName: String, chapterCount: String) {
    val bookViewModel: BookVM = viewModel()
    LaunchedEffect(Unit) {
        bookViewModel.loadListLinkApi(bookName, chapterCount)

    }
    val data by bookViewModel.listLinks
    Column() {
        LazyColumn() {
            items(data?.imageUrls.orEmpty()) {url ->
//                Text(text = url)
                ConfigImage(url)
            }
        }
    }
}