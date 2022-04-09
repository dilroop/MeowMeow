package com.dsb.meowmeow.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable


private val DarkColorPalette = darkColors(
    primary = DarkTheme_Primary,
    primaryVariant = DarkTheme_PrimaryDark,
    secondary = DarkTheme_Secondary,
    secondaryVariant = DarkTheme_SecondaryDark,
)

private val LightColorPalette = lightColors(
    primary = PrimaryCyan200,
    primaryVariant = PrimaryDark,
    secondary = SecondaryBlue200,
    secondaryVariant = SecondaryDark,
)

@Composable
fun MeowMeowTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
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