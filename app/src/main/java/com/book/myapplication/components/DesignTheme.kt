package com.book.myapplication.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.book.myapplication.ViewModel.BookVM
import com.book.myapplication.model.Book


//@Composable
//fun DesignTheme() {
//    Column(
//        modifier = Modifier.fillMaxSize(),
//
//    ) {
//        Row(modifier = Modifier.fillMaxWidth().height(300.dp)) {
//            RenderImage(imageName = "Attack_on_Titan")
//        }
//
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(start = 16.dp, end = 16.dp),
//            horizontalArrangement = Arrangement.SpaceAround
//        ) {
//            Column(modifier = Modifier .padding(8.dp)) {
//                Text(text = "58.1K", fontWeight = FontWeight.Bold, fontSize = 24.sp)
//                Text(text = "likes", fontWeight = FontWeight.Light)
//            }
//            Column(modifier = Modifier .padding(8.dp)) {
//                Text(text = "1.87M", fontWeight = FontWeight.Bold, fontSize = 24.sp)
//                Text(text = "follow", fontWeight = FontWeight.Light)
//            }
//            Column(modifier = Modifier .padding(8.dp)) {
//                Row(
//                    horizontalArrangement = Arrangement.Center,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Text(text = "4.7", fontWeight = FontWeight.Bold, fontSize = 24.sp)
//                    Icon(Icons.Filled.Star, "",
//                        tint = Color.Yellow,
//                        modifier = Modifier.size(16.dp))
//                }
//                Text(text = "likes", fontWeight = FontWeight.Light)
//            }
//        }
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(start = 16.dp, end = 16.dp),
//            horizontalArrangement = Arrangement.SpaceAround
//        ) {
//            Text(text = "Episodes", fontWeight = FontWeight(600))
//        }
//        Column {
//            val navController = rememberNavController()
//            ChapterList(navController,"Conan",16)
//        }
//    }
//}




fun listBookFakeData(): MutableList<Book> {
    val res : MutableList<Book> = mutableListOf()
    for(i in 1..30) {
        val data = Book("1",
            "Attack_on_Titan",
            i + 1,
            "Description 1",
            1)
        res.add(data)
    }
    return res
}
@Composable
fun StoryCard1(
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

@Composable
fun BookListHorizon(book_vm: BookVM,onBookClick: (Book) -> Unit) {
    val listBooks = (book_vm.loadTopTenBook() ?: emptyList()).toMutableList()
    LazyRow() {
        items(listBooks) {item ->
            StoryCard1(item, onBookClick)
        }
    }
}
//@Preview(showBackground = true)
//@Composable
//fun DesignPreview() {
//    DesignTheme()
//}