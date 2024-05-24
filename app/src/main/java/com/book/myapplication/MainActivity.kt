package com.book.myapplication

import LocaleHelper
import LoginForm
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.book.myapplication.ViewModel.BookVM
import com.book.myapplication.ViewModel.LocalSettingsVM
import com.book.myapplication.ViewModel.UserVM
import com.book.myapplication.components.AboutAccount
import com.book.myapplication.components.AboutBook
import com.book.myapplication.components.FollowList
import com.book.myapplication.components.HistoryView
import com.book.myapplication.components.MainUi
import com.book.myapplication.components.ReadBook
import com.book.myapplication.components.ResultSearch
import com.book.myapplication.components.ScreenView
import com.book.myapplication.components.Settings.LanguageView
import com.book.myapplication.components.Settings.SettingView
import com.book.myapplication.ui.theme.MyappTheme

class MainActivity : ComponentActivity() {
    private val viewModel: LocalSettingsVM by viewModels()
    override fun attachBaseContext(newBase: Context?) {
        val newLocal = LocaleHelper.wrap(newBase!!, "en") //Default is English
        super.attachBaseContext(newLocal)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val language = viewModel.language.collectAsState()
            val context = LocalContext.current
            var currentLanguage by remember { mutableStateOf("") }
            LaunchedEffect(language) {
                if (currentLanguage != language.value) {
                    currentLanguage = language.value
                    LocaleHelper.wrap(context, language.value)
                    Log.i("resultAPI", "language : ${language.value}")
                    // Restart activity to apply new language
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                    (context as ComponentActivity).finish()
                }
            }
            MyappTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyThemeLayout(viewModel)
                }
            }
        }
    }
}


@Composable
fun Navigator(viewModel: LocalSettingsVM) {
    val navController = rememberNavController()
    val dataUserVM: UserVM = viewModel()
    NavHost(navController = navController, startDestination = "main") {
        composable(route = "login") {
            LoginForm(navController, dataUserVM)
        }
        composable(route = "main") {
            MainUi(
                navController
            )
        }
        composable(route = "about-book/{id}") { backStackEntry ->
            backStackEntry.arguments?.getString("id")
                ?.let { AboutBook(navController = navController, it) }
        }
        composable(route = "read-book/{id}/{chapter}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            val chapter = backStackEntry.arguments?.getString("chapter") ?: ""
            ReadBook(id, chapter)
        }
        composable(route = "follow/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            FollowList(navController, idUser = id)
        }
        composable(route = "result-search/{name}") { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            ResultSearch(navController, name = name)
        }
        composable(route = "account") { backStackEntry ->
            AboutAccount(navController)
        }
        composable(route = "history") { backStackEntry ->
            HistoryView(navController)
        }
        composable<ScreenView.SettingView>{backStackEntry ->
            val args = backStackEntry.toRoute<ScreenView.SettingView>()
            SettingView(navController,viewModel, args.id.toString())
        }
        composable<ScreenView.LanguageView>{backStackEntry ->
            val args = backStackEntry.toRoute<ScreenView.LanguageView>()
            LanguageView()
        }
    }
}

@Composable
fun MyThemeLayout(viewModel : LocalSettingsVM) {
    Navigator(viewModel)
}