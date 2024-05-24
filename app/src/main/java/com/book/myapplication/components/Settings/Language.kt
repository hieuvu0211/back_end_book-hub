package com.book.myapplication.components.Settings

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.book.myapplication.ViewModel.LocalSettingsVM

@Composable
fun LanguageView(viewModel : LocalSettingsVM = viewModel()) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth().clickable {
            Toast.makeText(context, "done", Toast.LENGTH_SHORT).show()
        }) {
            viewModel.changeLanguage("en")
            Text(text = "English", fontSize = 20.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(modifier = Modifier.fillMaxWidth().clickable {
            Toast.makeText(context, "Xong", Toast.LENGTH_SHORT).show()
        }) {
            viewModel.changeLanguage("vi")
            Text(text = "Tiếng Việt", fontSize = 20.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShowLanguagePreview(modifier: Modifier = Modifier) {
    LanguageView()
}