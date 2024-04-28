package com.book.myapplication.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.book.myapplication.VM.BookVM


@SuppressLint("SuspiciousIndentation")
@Composable
fun RenderImage(
    imageName :String
) {
    val url: String = "http://10.0.2.2:8080/Books/${imageName}/image.png"
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
                .size(120.dp)
                .clip(RoundedCornerShape(25.dp))
        )
}

@Composable
fun ChapterList(navController: NavController,chapterName :String, chapterCount: Int) {
    val chapters = (1..chapterCount).map { it }
    // Fixed height for the parent container
    Column(modifier = Modifier.height(400.dp)) {
        // LazyColumn to render the list of chapters
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(chapters) { chapter ->
                ChapterItem(navController,chapterName,chapter.toString())
            }
        }
    }
}

@Composable
fun ChapterItem(navController: NavController,chapterName :String,chapterCount: String) {
    // Each item in the list is a Text composable
    Box(modifier = Modifier.clickable {
        navController.navigate("read-book/$chapterName/$chapterCount")
        Log.i("resultAPI", "name = $chapterName , chapter = $chapterCount")
    }) {
        Text(
            text = "Chapter $chapterCount",
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }

}
@Composable
fun AboutBook(navController: NavController, id : String) {
    val book_vm : BookVM = viewModel()
    val data by book_vm.book_data
    LaunchedEffect(Unit) {
        book_vm.loadBookById(id)
    }
    //data : book_name, author?, number_of_chapter, description
    val name: String = data?.book_name ?: ""
    val chapter : Int = data?.number_of_chapter ?: 1
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        Row {
//            render Image of book
            RenderImage(name)
            Column {
                //render book name and author ...

                (if (name.length > 20) {
                    "${name.take(20)}..."
                }else {
                    name
                }).let {
                    Text(
                        text = it
                    )
                }

                Text(text = "number of chapter : $chapter")
                Row {
                    Button(onClick = { /*TODO*/ }) {
                        Text(text = "Chapter 1")
                    }
                    Button(onClick = { /*TODO*/ }) {
                        Text(text = "Last chapter")
                    }
                }

            }
        }
        ChapterList(navController,name,chapter)
        Text(text = "This is text")

    }
}