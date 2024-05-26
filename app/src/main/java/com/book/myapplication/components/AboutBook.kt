package com.book.myapplication.components

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.book.myapplication.GlobalState.UserData
import com.book.myapplication.R
import com.book.myapplication.ViewModel.BookVM
import com.book.myapplication.ViewModel.FavoriteVM
import com.book.myapplication.ViewModel.HistoryVM
import com.book.myapplication.model.Favorite1
import com.book.myapplication.model.History
import com.book.myapplication.model.User
import java.util.Date

@SuppressLint("SuspiciousIndentation")
@Composable
fun RenderImage(
    idBook :String
) {
    val url = "http://10.0.2.2:8080/Books/${idBook}/image.png"
    val painter =
        rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current).data(data = url)
                .apply(block = fun ImageRequest.Builder.() {
                    // You can customize image loading parameters here
                }).build()
        )
        Box(modifier = Modifier
            .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Back")
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    ,
                contentScale = ContentScale.FillBounds
            )
        }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChapterList(navController: NavController, idBook:String, chapterCount: Int, data: History) {
    val chapters = (1..chapterCount).map { it }
    val historyViewModel = viewModel<HistoryVM>()
    // Fixed height for the parent container
    Column(modifier = Modifier.height(400.dp)) {
        // LazyColumn to render the list of chapters
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            items(chapters) { chapter ->
                ChapterItem(navController,idBook,chapter.toString()) {
                    if(chapter == chapterCount) {
                        data.status = "Completed"
                    }else{
                        data.status = "Reading"
                    }
                    data.last_read_page = chapter
                    historyViewModel.updateHistory(data)
                }
            }
        }
    }
}

@Composable
fun ChapterItem(
    navController: NavController,
    idBook:String,
    chapterCount: String,
    handleUpdateHistory: () -> Unit
) {
    // Each item in the list is a Text composable
    Box(modifier = Modifier.clickable {
        //handle post data to backend to update history
        handleUpdateHistory()
        //handle navigate to read book
        navController.navigate("read-book/$idBook/$chapterCount")
    }) {
        Text(
            text = "Chapter $chapterCount",
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AboutBook(navController: NavController, bookId : String) {
    val context = LocalContext.current
    val dataUserStore = UserData(context)
    val getDataUserFromLocal =
        dataUserStore.getDataUserFromLocal.collectAsStateWithLifecycle(initialValue = User(0, "", ""))
    var idUser by rememberSaveable {
        mutableIntStateOf(0)
    }
    idUser = getDataUserFromLocal.value?.user_id ?: 0
    val bookViewModel : BookVM = viewModel()
    val favoriteViewModel : FavoriteVM = viewModel()
    val historyViewModel = viewModel<HistoryVM>()
    val data by bookViewModel.book_data
    LaunchedEffect(Unit) {
        bookViewModel.loadBookById(bookId)

    }
    var isFollow by rememberSaveable {
        mutableStateOf(false)
    }
    var dataFavorite = Favorite1(0,0)
    val historyDataSend by remember {
        mutableStateOf(History(0,bookId.toInt(), data?.book_name ?: "", Date().toString(),0,"Reading"))
    }
    if(idUser != 0) {
        dataFavorite = Favorite1( idUser, bookId.toInt())
        favoriteViewModel.IsFollow(dataFavorite)
        historyDataSend.user_id = idUser
        historyViewModel.checkIsRead("${idUser}-${bookId}")
    }

    favoriteViewModel.isFollowLiveData.observeForever { newValue -> isFollow = newValue }
    val lastRead by historyViewModel.lastChapterRead.collectAsStateWithLifecycle()
    val numberOfChapter : Int = data?.number_of_chapter ?: 1
    val numberOfLikes : Int = data?.number_of_likes ?: 1

    Column(
        modifier = Modifier.fillMaxSize(),

        ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)) {
            data?.book_id?.let { RenderImage(idBook = it) }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column(modifier = Modifier .padding(8.dp)) {
                Text(text = numberOfLikes.toString(), fontWeight = FontWeight.Bold, fontSize = 24.sp)
                Text(text = stringResource(id = R.string.likes), fontWeight = FontWeight.Light)
            }
            Column(modifier = Modifier .padding(8.dp)) {
                Text(text = "1.87M", fontWeight = FontWeight.Bold, fontSize = 24.sp)
                Text(text = stringResource(id = R.string.Follows), fontWeight = FontWeight.Light)
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
                Text(text = stringResource(id = R.string.Rate), fontWeight = FontWeight.Light)
            }
        }
        Row {
            if(isFollow) {
                Button(onClick = {
                    favoriteViewModel.DeleteFromFavorite("$idUser-$bookId")
                    isFollow = false
                },
                    modifier = Modifier.padding(start = 24.dp),
                    colors = ButtonDefaults.buttonColors()) {
                    Text(text = stringResource(id = R.string.Unfollow))
                }
            }else{
                Button(onClick = {
                    favoriteViewModel.AddToFavorite(dataFavorite)
                    isFollow = true
                },
                    modifier = Modifier.padding(start = 24.dp),
                    colors = ButtonDefaults.buttonColors()) {
                    Text(text = stringResource(id = R.string.Follow))
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            //handle if user already read this book else do not render Button
            if(lastRead != null && lastRead != 0) {
                Button(onClick = {
                    navController.navigate("read-book/${bookId}/${lastRead}")
                }) {
                    Text(text = stringResource(id = R.string._continue))
                }
            }

        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(text = stringResource(id = R.string.Episodes), fontWeight = FontWeight(600))
        }
        Column {
            data?.book_id?.let { ChapterList(navController, it ,numberOfChapter, historyDataSend)}
        }
    }
}

