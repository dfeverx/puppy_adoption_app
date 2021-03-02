package com.dfx.puppyadoption.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    background = BackgroundDark,
    onBackground = OnBackgroundDark,
    surface = DefaultCardBackgroundDark,
    onSurface = DefaultCardTextDark,
    secondary = BackgroundVariantDark,
    onSecondary = OnBackgroundDark,


    )

private val LightColorPalette = lightColors(


    background = BackgroundLight,
    onBackground = OnBackgroundLight,
    surface = DefaultCardBackgroundLight,
    onSurface = DefaultCardTextLight,
    secondary = BackgroundVariantLight,
    onSecondary = OnBackgroundLight
    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun ComposeStarterTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )

}