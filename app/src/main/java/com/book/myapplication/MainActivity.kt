package com.book.myapplication

import LoginForm
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.book.myapplication.GlobalState.DarkModeData
import com.book.myapplication.GlobalState.LanguageData
import com.book.myapplication.LocalSettings.updateLocale
import com.book.myapplication.ViewModel.AuthVM
import com.book.myapplication.ViewModel.DarkModeVM
import com.book.myapplication.ViewModel.LanguageVM
import com.book.myapplication.ViewModel.UserVM
import com.book.myapplication.components.AboutAccount
import com.book.myapplication.components.AboutBook
import com.book.myapplication.components.FollowList
import com.book.myapplication.components.HistoryView
import com.book.myapplication.components.LoginAuth
import com.book.myapplication.components.MainUi
import com.book.myapplication.components.ReadBook
import com.book.myapplication.components.ResultSearch
import com.book.myapplication.components.ScreenView
import com.book.myapplication.components.Settings.DarkModeView
import com.book.myapplication.components.Settings.LanguageView
import com.book.myapplication.components.Settings.SettingView
import com.book.myapplication.ui.theme.MyappTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class MainActivity : ComponentActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient
    private val viewModel: AuthVM by viewModels()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        val signInLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            viewModel.handleSignInResult(task)
        }

        viewModel.signInIntent.observe(this) { intent ->
            intent?.let {
                signInLauncher.launch(it)
                viewModel.onSignInIntentHandled()
            }
        }
        setContent {
            //handle get data if user set isDarkTheme
            val prefsDarkMode = DarkModeData(this)

            //create viewModel for change theme to Dark Mode
            val darkThemeViewModel = viewModel<DarkModeVM>(
                factory = viewModelFactory {
                    initializer {
                        DarkModeVM(prefsDarkMode)
                    }
                }
            )
            val isDarkTheme by darkThemeViewModel.isDarkTheme.collectAsStateWithLifecycle()
            MyappTheme(
                darkTheme = isDarkTheme
            ) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyThemeLayout(context = this, darkThemeViewModel = darkThemeViewModel, viewModel)
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigator(context : Context, darkThemeViewModel : DarkModeVM, authViewModel : AuthVM) {

    val navController = rememberNavController()
    val dataUserVM: UserVM = viewModel()
    val coroutineScope = rememberCoroutineScope()

    NavHost(navController = navController, startDestination = "main") {
        composable(route = "login") {

            LoginForm(navController, dataUserVM, authViewModel)
        }
        composable(route = "main") {
            MainUi(navController)
        }
        composable(route = "about-book/{idBook}") { backStackEntry ->
            backStackEntry.arguments?.getString("idBook")
                ?.let { AboutBook(navController = navController, it) }
        }
        composable(route = "read-book/{idBook}/{idChapter}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("idBook") ?: ""
            val chapter = backStackEntry.arguments?.getString("idChapter") ?: ""
            ReadBook(id, chapter)
        }
        composable(route = "follow/{idUser}") { backStackEntry ->
            val idUser = backStackEntry.arguments?.getString("idUser") ?: ""
            FollowList(navController, idUser = idUser)
        }
        composable(route = "result-search/{name}") { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            ResultSearch(navController, name = name)
        }
        composable(route = "account") { backStackEntry ->
            AboutAccount(navController, authViewModel)
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
        composable<ScreenView.DarkModeView>{
            DarkModeView(navController = navController, darkThemeViewModel = darkThemeViewModel)
        }
        composable(route = "auth-login") {
            LoginAuth(authViewModel = authViewModel)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyThemeLayout(context : Context, darkThemeViewModel : DarkModeVM, authViewModel : AuthVM) {

    //update language
    val context2 = LocalContext.current
    val prefsLanguage = LanguageData(context2)
    val languageViewmodel : LanguageVM = viewModel(
        factory = viewModelFactory {
            initializer {
                LanguageVM(prefsLanguage)
            }
        }
    )
    val locale = languageViewmodel.locale.collectAsState()
    updateLocale(context, locale.value)

    //update theme
    val isSystemDarkTheme = isSystemInDarkTheme()
    val isFollowSystemTheme by darkThemeViewModel.isFollowSystemTheme.collectAsStateWithLifecycle()
    if(isFollowSystemTheme) {
        darkThemeViewModel.changeDarkTheme(isSystemDarkTheme)
    }

    Navigator(context, darkThemeViewModel, authViewModel)
}