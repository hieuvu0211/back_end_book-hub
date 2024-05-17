package com.book.myapplication.components.Settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingView() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        ) {
            Icon(Icons.Default.ArrowBack, "", modifier = Modifier.size(40.dp))
            Spacer(modifier = Modifier.weight(0.8f))
            Text(text = "Settings", fontSize = 30.sp, fontWeight = FontWeight(600))
            Spacer(modifier = Modifier.weight(0.9f))
        }
        ListSettings()
        
    }
}

@Composable
fun ListSettings() {
    val listContents = listOf("Dark mode", "Language", "Privacy Policy", "Terms of Service")
    listContents.forEach {item ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .size(30.dp)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = item)
            Spacer(modifier = Modifier.weight(1f))
            Icon(Icons.Default.KeyboardArrowRight, "")
        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}
@Preview(showBackground = true)
@Composable
fun SettingPreview(modifier: Modifier = Modifier) {
    SettingView()
}