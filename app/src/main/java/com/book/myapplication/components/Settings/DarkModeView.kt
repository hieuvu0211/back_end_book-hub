package com.book.myapplication.components.Settings

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.book.myapplication.R
import com.book.myapplication.ViewModel.DarkModeVM
import com.book.myapplication.components.ScreenView

@Composable
fun DarkModeView(navController : NavController, darkThemeViewModel: DarkModeVM){
    val context = LocalContext.current
    val toastMsgLightMode = stringResource(id = R.string.light_mode_is_turn_on)
    val toastMsgDarkMode = stringResource(id = R.string.dark_mode_is_turn_on)
    val checkSystemMode = isSystemInDarkTheme()

    val toastMsgFollowSystem = if(checkSystemMode) toastMsgDarkMode else toastMsgLightMode

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack, "", modifier = Modifier
                    .size(40.dp)
                    .clickable {
                        navController.navigate(ScreenView.SettingView("0"))
                    })
            Spacer(modifier = Modifier.weight(0.8f))
            Text(text = stringResource(id = R.string.dark_mode), fontSize = 30.sp, fontWeight = FontWeight(600))
            Spacer(modifier = Modifier.weight(0.9f))
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .clickable {

            }) {
            Button(onClick = {
                darkThemeViewModel.changeDarkTheme(false)
                darkThemeViewModel.changeFollowSystemTheme(false)
                Toast
                    .makeText(context, toastMsgLightMode , Toast.LENGTH_SHORT)
                    .show()
            },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color.White),
                shape = ButtonDefaults.shape) {
                Text(text = stringResource(id = R.string.light_mode), color = Color.Black)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(modifier = Modifier
            .fillMaxWidth()
        ) {
            Button(onClick = {
                darkThemeViewModel.changeDarkTheme(true)
                darkThemeViewModel.changeFollowSystemTheme(false)
                Toast
                    .makeText(context, toastMsgDarkMode, Toast.LENGTH_SHORT)
                    .show()
            },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color.White),
                shape = ButtonDefaults.shape) {
                Text(text = stringResource(id = R.string.dark_mode), color = Color.Black)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(modifier = Modifier
            .fillMaxWidth()
        ) {
            Button(onClick = {
                darkThemeViewModel.changeFollowSystemTheme(true)
                Toast
                    .makeText(context, toastMsgFollowSystem, Toast.LENGTH_SHORT)
                    .show()
            },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color.White),
                shape = ButtonDefaults.shape) {
                Text(text = stringResource(id = R.string.follow_system), color = Color.Black)
            }
        }
    }
}