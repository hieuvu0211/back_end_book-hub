package com.book.myapplication.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.book.myapplication.GlobalState.UserData
import com.book.myapplication.ViewModel.HistoryVM
import com.book.myapplication.model.History
import com.book.myapplication.model.User

@Composable
fun HistoryView(navController: NavController) {
    val context = LocalContext.current
    val dataUserStore = UserData(context)
    val dataUser =
        dataUserStore.getDataUserFromLocal.collectAsState(initial = User(0, "", ""))
    var idUser by rememberSaveable {
        mutableIntStateOf(0)
    }
    idUser = dataUser.value?.user_id ?: 0
    val history_vm : HistoryVM = viewModel()
    if(idUser != 0) {
        LaunchedEffect(Unit) {
            history_vm.loadListHistory(idUser.toString())
        }
    }

    val listHistory by history_vm.listHistory.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "Your Histories", fontSize = 30.sp)
            Spacer(modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(12.dp))
        ListCardHistory(data = listHistory)
    }
}
@Composable
fun ListCardHistory(data : List<History>) {
    LazyColumn {
        items(data) {item ->
            CardHistory(data = item)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}
@Composable
fun CardHistory(data : History) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
    ) {
        Image(painter = rememberAsyncImagePainter("http://10.0.2.2:8080/Books/${data.book_name}/image.png"), "",
            modifier = Modifier
                .width(120.dp)
                .fillMaxHeight())
        Spacer(modifier = Modifier.width(12.dp))
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        ) {
            Text(text = data.book_name, fontSize = 25.sp, fontWeight = FontWeight(600))
            Text(text = "Reading on chap: ${data.last_read_page}")
            Row {
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Read")
                }
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Continue")
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