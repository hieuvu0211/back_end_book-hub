package com.book.myapplication

import LoginForm
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.book.myapplication.GlobalState.LanguageData
import com.book.myapplication.LocalSettings.updateLocale
import com.book.myapplication.ViewModel.LanguageVM
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
import java.util.Locale

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
                    MyThemeLayout(context = this)
                }
            }
        }
    }
}


@Composable
fun Navigator(context : Context) {
    val context2 = LocalContext.current
    val prefsLanguage = LanguageData(context2)
    val language_vm : LanguageVM = viewModel(
        factory = viewModelFactory {
            initializer {
                LanguageVM(prefsLanguage)
            }
        }
    )
    val locale = language_vm.locale.collectAsState()
    updateLocale(context, locale.value)
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
            SettingView(navController, args.id.toString())
        }
        composable<ScreenView.LanguageView>{backStackEntry ->
            val args = backStackEntry.toRoute<ScreenView.LanguageView>()
            LanguageView(navController,context)
        }
    }
}

@Composable
fun MyThemeLayout(context : Context) {
    Navigator(context)
}