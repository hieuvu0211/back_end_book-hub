package com.book.myapplication

import LoginForm
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.book.myapplication.ViewModel.BookVM
import com.book.myapplication.ViewModel.UserVM
import com.book.myapplication.components.AboutAccount
import com.book.myapplication.components.AboutBook
import com.book.myapplication.components.FollowList
import com.book.myapplication.components.HistoryView
import com.book.myapplication.components.MainUi
import com.book.myapplication.components.ReadBook
import com.book.myapplication.components.ResultSearch
import com.book.myapplication.ui.theme.MyappTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyappTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyThemeLayout()
                }
            }
        }
    }
}


@Composable
fun Navigator() {
    val navController = rememberNavController()
    val dataUserVM: UserVM = viewModel()
    val book_vm : BookVM = viewModel()
    NavHost(navController = navController, startDestination = "main") {
        composable(route = "login") {
            LoginForm(navController, dataUserVM)
        }
        composable(route = "main") {
            MainUi(
                navController
            )
        }
        composable(route = "about-book/{id}") {backStackEntry ->
            backStackEntry.arguments?.getString("id")
                ?.let { AboutBook(navController = navController, it) }
        }
        composable(route = "read-book/{id}/{chapter}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            val chapter = backStackEntry.arguments?.getString("chapter") ?: ""
            ReadBook(id, chapter)
        }
        composable(route = "follow/{id}") {backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            FollowList(navController,idUser = id)
        }
        composable(route = "result-search/{name}") {backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            ResultSearch(navController,name = name)
        }
        composable(route = "account") {backStackEntry ->
            AboutAccount(navController)
        }
        composable(route = "history") {backStackEntry ->
            HistoryView(navController)
        }
    }
}



@Composable
fun MyThemeLayout(modifier: Modifier = Modifier) {
    Navigator()
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyappTheme {
        Column(modifier = Modifier.fillMaxWidth()) {
            MyThemeLayout()
        }

    }
}