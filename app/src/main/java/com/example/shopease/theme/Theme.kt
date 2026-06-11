package com.example.shopease.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryDark,
    secondary = Secondary,
    onSecondary = OnSecondary,
    secondaryContainer = Color(0xFF1E2732),
    tertiary = Accent,
    onTertiary = Color(0xFF131921),
    background = Color(0xFF131921),
    onBackground = Color(0xFFFFFFFF),
    surface = Color(0xFF232F3E),
    onSurface = Color(0xFFFFFFFF),
    surfaceVariant = Color(0xFF37475A),
    onSurfaceVariant = Color(0xFFDDDDDD),
    error = Error,
    onError = OnPrimary,
    outline = SurfaceHighlight
)

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryLight,
    secondary = Secondary,
    onSecondary = OnSecondary,
    tertiary = Accent,
    background = Background,
    onBackground = OnBackground,
    surface = Surface,
    onSurface = OnSurface,
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = OnSurfaceVariant,
    error = Error,
    onError = OnPrimary,
    outline = SurfaceHighlight
)

@Composable
fun ShopEaseTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
