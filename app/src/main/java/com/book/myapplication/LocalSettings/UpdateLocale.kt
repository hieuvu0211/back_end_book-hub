package com.book.myapplication.LocalSettings

import android.content.Context
import android.content.res.Configuration
import java.util.Locale

fun updateLocale(context: Context, locale: Locale) {
    Locale.setDefault(locale)
    val config = Configuration()
    config.setLocale(locale)
    context.resources.updateConfiguration(config, context.resources.displayMetrics)
}