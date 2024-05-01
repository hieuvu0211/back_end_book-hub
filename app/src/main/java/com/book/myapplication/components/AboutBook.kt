package com.book.myapplication.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.book.myapplication.R
import com.book.myapplication.VM.BookVM


//@SuppressLint("SuspiciousIndentation")
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
        Box(modifier = Modifier
//            .size(300.dp)
            .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Back")
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
//                    .clip(RoundedCornerShape(25.dp))
                    ,
                contentScale = ContentScale.FillBounds
            )
        }

}

@Composable
fun ChapterList(navController: NavController,chapterName :String, chapterCount: Int) {
    val chapters = (1..chapterCount).map { it }
    // Fixed height for the parent container
    Column(modifier = Modifier.height(400.dp)) {
        // LazyColumn to render the list of chapters
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
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
    val numberOfChapter : Int = data?.number_of_chapter ?: 1
    val numberOfLikes : Int = data?.number_of_likes ?: 1
    Column(
        modifier = Modifier.fillMaxSize(),

        ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)) {
            RenderImage(imageName = name)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column(modifier = Modifier .padding(8.dp)) {
                Text(text = numberOfLikes.toString(), fontWeight = FontWeight.Bold, fontSize = 24.sp)
                Text(text = "likes", fontWeight = FontWeight.Light)
            }
            Column(modifier = Modifier .padding(8.dp)) {
                Text(text = "1.87M", fontWeight = FontWeight.Bold, fontSize = 24.sp)
                Text(text = "follow", fontWeight = FontWeight.Light)
            }
            Column(modifier = Modifier .padding(8.dp)) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "4.7", fontWeight = FontWeight.Bold, fontSize = 24.sp)
                    Icon(
                        Icons.Filled.Star, "",
                        tint = Color.Yellow,
                        modifier = Modifier.size(16.dp))
                }
                Text(text = "rate", fontWeight = FontWeight.Light)
            }
        }
        Row {
            var isFollow by rememberSaveable {
                mutableStateOf(false)
            }
            ///code here later
            Button(onClick = { /*TODO*/ },
                modifier = Modifier.padding(start = 24.dp),
                colors = ButtonDefaults.buttonColors()) {
                Text(text = "Follow")
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(text = "Episodes", fontWeight = FontWeight(600))
        }
        Column {
            ChapterList(navController,name,numberOfChapter)
        }
    }
}

