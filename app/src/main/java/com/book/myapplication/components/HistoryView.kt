package com.book.myapplication.components

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.book.myapplication.GlobalState.UserData
import com.book.myapplication.R
import com.book.myapplication.ViewModel.HistoryVM
import com.book.myapplication.model.History
import com.book.myapplication.model.User

@Composable
fun HistoryView(navController: NavController) {
    val context = LocalContext.current
    val dataUserStore = UserData(context)
    val dataUser =
        dataUserStore.getDataUserFromLocal.collectAsStateWithLifecycle(
            initialValue = User(
                0,
                "",
                ""
            )
        )
    var idUser by rememberSaveable {
        mutableIntStateOf(0)
    }
    idUser = dataUser.value?.user_id ?: 0
    val historyViewModel: HistoryVM = viewModel()
    if (idUser != 0) {
        LaunchedEffect(Unit) {
            historyViewModel.loadListHistory(idUser.toString())
        }
    }

    val listHistory by historyViewModel.listHistory.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack, "back_to_account",
                modifier = Modifier
                    .size(32.dp)
                    .clickable {
                        navController.navigate("account")
                    }
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(text = stringResource(id = R.string.your_histories), fontSize = 30.sp)
            Spacer(modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(12.dp))
        ListCardHistory(navController, data = listHistory)
    }
}

@Composable
fun ListCardHistory(
    navController: NavController,
    data: List<History>
) {
    LazyColumn {
        items(data) { item ->
            CardHistory(item, handleReadChapter1 = {
                    navController.navigate("about-book/${item.book_id}")
            }, handleContinueReading = {
                    navController.navigate("read-book/${item.book_id}/${item.last_read_page}")
            })
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun CardHistory(
    data: History,
    handleReadChapter1: (History) -> Unit,
    handleContinueReading: (History) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter("http://10.0.2.2:8080/Books/${data.book_id}/image.png"),
            "",
            modifier = Modifier
                .width(120.dp)
                .fillMaxHeight()
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        ) {
            Text(text = data.book_name, fontSize = 25.sp, fontWeight = FontWeight(600))
            Text(text = stringResource(id = R.string.reading_on_chap) + ": ${data.last_read_page}")
            Row {
                Button(onClick = { handleReadChapter1(data) }) {
                    Text(text = stringResource(id = R.string.read))
                }
                Button(onClick = { handleContinueReading(data) }) {
                    Text(text = stringResource(id = R.string._continue))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HistoryPreview(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    HistoryView(navController)
}