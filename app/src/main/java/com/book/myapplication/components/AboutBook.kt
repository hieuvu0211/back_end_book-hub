package com.book.myapplication.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.book.myapplication.VM.BookVM
import com.book.myapplication.model.Book


@Composable
fun ChapterList(chapterCount: Int) {
    val chapters = (1..chapterCount).map { "Chapter $it" }
    // Fixed height for the parent container
    Column(modifier = Modifier.height(200.dp)) {
        // LazyColumn to render the list of chapters
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(chapters) { chapter ->
                ChapterItem(chapterName = chapter)
            }
        }
    }
}

@Composable
fun ChapterItem(chapterName: String) {
    // Each item in the list is a Text composable
    Text(
        text = chapterName,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
}
@Composable
fun AboutBook(navController: NavController) {
    val book_vm : BookVM = viewModel()
    val data : MutableState<Book?> = book_vm.getBookData()
    //data : book_name, author?, number_of_chapter, description
    val name: String? = data.value?.book_name
    val chapter : Int? = data.value?.number_of_chapter
    val url = "http://10.0.2.2:8080/Books/${data.value?.book_name}/image.png"
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        Row {
            //render Image of book
            ImageFromLocalhostUrl(navController ,data)
            Column {
                //render book name and author ...

                if (name != null) {
                    (if (name.length > 10) {
                        "${name.take(10)}..."
                    }else {
                        name
                    }).let {
                        Text(
                            text = it
                        )
                    }
                }

                Text(text = "number of chapter : $chapter")
            }
        }
        if (chapter != null) {
            ChapterList(chapter)
        }

    }
}