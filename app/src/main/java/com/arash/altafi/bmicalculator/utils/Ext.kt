package com.arash.altafi.bmicalculator.utils

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate

fun Context.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun isNightModeEnabled(): Boolean {
    return when (AppCompatDelegate.getDefaultNightMode()) {
        AppCompatDelegate.MODE_NIGHT_YES -> true
        AppCompatDelegate.MODE_NIGHT_UNSPECIFIED -> true
        else -> false
    }
}