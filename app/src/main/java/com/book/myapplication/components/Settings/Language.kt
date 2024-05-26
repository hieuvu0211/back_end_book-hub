package com.book.myapplication.components.Settings

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.book.myapplication.GlobalState.LanguageData
import com.book.myapplication.R
import com.book.myapplication.ViewModel.LanguageVM
import java.util.Locale

@Composable
fun LanguageView(navController: NavController, context : Context) {
    val context2 = LocalContext.current
    val prefsLanguage = LanguageData(context2)

    val languageViewModel : LanguageVM = viewModel(
        factory = viewModelFactory {
            initializer {
                LanguageVM(prefsLanguage)
            }
        }
    )
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
                        navController.navigate("account")
                    })
            Spacer(modifier = Modifier.weight(0.8f))
            Text(text = stringResource(id = R.string.language), fontSize = 30.sp, fontWeight = FontWeight(600))
            Spacer(modifier = Modifier.weight(0.9f))
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .clickable {

            }) {
            Button(onClick = {
                languageViewModel.updateLocale(Locale("en"))
                Toast
                    .makeText(context, "done", Toast.LENGTH_SHORT)
                    .show()
            },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color.White),
                shape = ButtonDefaults.shape) {
                Text(text = "English", color = Color.Black)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(modifier = Modifier
            .fillMaxWidth()
            ) {
            Button(onClick = {
                languageViewModel.updateLocale(Locale("vi"))
                Toast
                    .makeText(context, "Xong", Toast.LENGTH_SHORT)
                    .show()
            },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color.White),
                shape = ButtonDefaults.shape) {
                Text(text = "Vietnamese", color = Color.Black)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShowLanguagePreview(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val navController = rememberNavController()
    LanguageView(navController,context)
}