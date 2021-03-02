package com.dfx.puppyadoption.ui.utils

import android.os.Build
import android.view.View
import android.view.Window
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dfx.puppyadoption.R
import com.dfx.puppyadoption.ui.theme.Typography

import androidx.annotation.RequiresApi





@Composable
fun MyToolbar(
    isHome: Boolean = false, onBackPressed: (() -> (Unit))? = null,
    changeTheme: (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier
            .height(56.dp)
            .padding(4.dp)
            .fillMaxWidth()
    ) {
        if (isHome) {
            IconButton(
                onClick = {/*profile click*/ }, modifier = Modifier
                    .size(48.dp)
                    .padding(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription =null,
                    tint = MaterialTheme.colors.onBackground
                )
            }
        } else {
            IconButton(
                onClick = { onBackPressed?.let { it() } }, modifier = Modifier
                    .size(48.dp)
                    .padding(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = null,
                    tint = MaterialTheme.colors.onBackground
                )
            }
        }

        Box(modifier = Modifier.weight(1f))
        if (isHome) {
            IconButton(
                onClick = { changeTheme?.let { it() } }, modifier = Modifier
                    .size(48.dp)
                    .padding(12.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_brightnesss),
                    contentDescription =null,
                    tint = MaterialTheme.colors.onBackground
                )
            }
        }
    }

}

@Composable
fun Wishing(userName: String, wish: String) {
    Column(
        modifier = Modifier

            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp)
    ) {
        Text(text = "Hi $userName", color = MaterialTheme.colors.onBackground)
        Text(text = wish, style = Typography.h6, color = MaterialTheme.colors.onBackground)
    }

}

@Composable
fun WelcomeUser(
    userName: String, wish: String,
    isHome: Boolean = false,
    changeTheme: (() -> Unit)? = null,
) {

    Column(
        modifier = Modifier.background(
            MaterialTheme
                .colors.background
        )
    ) {
        MyToolbar(isHome = isHome, changeTheme = changeTheme)
        Wishing(userName, wish)
    }

}

@ExperimentalAnimationApi
@Composable
fun ToggleHeart(state: Boolean, update: (Boolean) -> Unit, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        IconButton(
            onClick = { update(!state) }, modifier = Modifier
                .fillMaxSize(1f)

        ) {
            if (state) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = null,
                    Modifier
                        .fillMaxSize(1f)
                        .padding(4.dp),
                    tint = MaterialTheme.colors.onBackground
                )
            } else {
                Icon(
                    imageVector = Icons.Filled.FavoriteBorder,
                    contentDescription = null,
                    Modifier
                        .fillMaxSize(1f)
                        .padding(4.dp),   tint = MaterialTheme.colors.onBackground
                )
            }
        }
    }
}


@Composable
fun SystemUi(windows: Window,isDark:Boolean) =
    MaterialTheme {
        windows.statusBarColor = MaterialTheme.colors.background.toArgb()
        windows.navigationBarColor = MaterialTheme.colors.background.toArgb()


        @Suppress("DEPRECATION")
        if (MaterialTheme.colors.surface.luminance() > 0.5f) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                windows.decorView.systemUiVisibility = windows.decorView.systemUiVisibility or
                        View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

            }
        }

        @Suppress("DEPRECATION")
        if (MaterialTheme.colors.surface.luminance() > 0.5f) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                windows.decorView.systemUiVisibility = windows.decorView.systemUiVisibility or
                        View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setSystemBarTheme(windows,isDark)
        }

    }

/** Changes the System Bar Theme.  */
@RequiresApi(api = Build.VERSION_CODES.M)
fun setSystemBarTheme(windows: Window, pIsDark: Boolean) {
    // Fetch the current flags.
    val lFlags = windows.decorView.systemUiVisibility
    // Update the SystemUiVisibility dependening on whether we want a Light or Dark theme.
    windows.decorView.systemUiVisibility =
        if (pIsDark) if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            lFlags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        } else {
            TODO("VERSION.SDK_INT < M")
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            lFlags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            TODO("VERSION.SDK_INT < M")
        }
}