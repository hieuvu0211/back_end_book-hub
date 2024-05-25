package com.book.myapplication.components.Settings

import android.content.Context
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.book.myapplication.R
import com.book.myapplication.ViewModel.LanguageVM
import com.book.myapplication.components.ScreenView

@Composable
fun SettingView(navController: NavController, id: String) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, "", modifier = Modifier
                .size(40.dp)
                .clickable {
                    navController.navigate("account")
                })
            Spacer(modifier = Modifier.weight(0.8f))
            Text(text = stringResource(id = R.string.settings), fontSize = 30.sp, fontWeight = FontWeight(600))
            Spacer(modifier = Modifier.weight(0.9f))
        }
        ListSettings(navController)

    }
}

@Composable
fun ListSettings(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .size(30.dp)
            .padding(horizontal = 8.dp)
            .clickable {

            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(id = R.string.dark_mode))
        Spacer(modifier = Modifier.weight(1f))
        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, "")
    }
    Spacer(modifier = Modifier.height(10.dp))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .size(30.dp)
            .padding(horizontal = 8.dp)
            .clickable {
                navController.navigate(ScreenView.LanguageView("vi"))
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(id = R.string.language))
        Spacer(modifier = Modifier.weight(1f))
        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, "")
    }
    Spacer(modifier = Modifier.height(10.dp))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .size(30.dp)
            .padding(horizontal = 8.dp)
            .clickable {

            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(id = R.string.privacy_policy))
        Spacer(modifier = Modifier.weight(1f))
        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, "")
    }
    Spacer(modifier = Modifier.height(10.dp))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .size(30.dp)
            .padding(horizontal = 8.dp)
            .clickable {

            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(id = R.string.terms_of_service))
        Spacer(modifier = Modifier.weight(1f))
        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, "")
    }
    Spacer(modifier = Modifier.height(10.dp))
}

@Preview(showBackground = true)
@Composable
fun SettingPreview(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    SettingView(navController, "")
}