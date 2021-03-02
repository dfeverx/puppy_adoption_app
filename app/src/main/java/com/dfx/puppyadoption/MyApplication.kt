package com.dfx.puppyadoption

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {
    val isDarkTheme = mutableStateOf(false)

    fun changeTheme() {
        isDarkTheme.value = !isDarkTheme.value
    }
}