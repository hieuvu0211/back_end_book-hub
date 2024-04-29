package com.book.myapplication.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun DesignTheme() {
    Text(text = "This is design main")
}

@Preview(showBackground = true)
@Composable
fun DesignPreview() {
    DesignTheme()
}