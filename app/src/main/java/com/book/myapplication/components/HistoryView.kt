package com.book.myapplication.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.book.myapplication.GlobalState.UserData
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
    Text(text = "This is text from HistoryView with idUser = $idUser")
}