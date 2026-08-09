package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val BusColorScheme = lightColorScheme(
    primary = BusAmberPrimary,
    onPrimary = BusMahoganyBackground,
    primaryContainer = BusPeachHighlight,
    onPrimaryContainer = BusTextLight,
    secondary = BusPeachHighlight,
    onSecondary = BusTextLight,
    secondaryContainer = BusSurfaceDark,
    onSecondaryContainer = BusTextLight,
    tertiary = BusAmberDark,
    onTertiary = BusMahoganyBackground,
    background = BusMahoganyBackground,
    onBackground = BusTextLight,
    surface = BusSurfaceDark,
    onSurface = BusTextLight,
    surfaceVariant = BusCardBackground,
    onSurfaceVariant = BusTextLight
)

@Composable
fun BusWaleBhaiyaTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = BusColorScheme
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            window.navigationBarColor = BusSurfaceDark.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

// Alias for backwards compatibility
@Composable
fun MyApplicationTheme(content: @Composable () -> Unit) {
    BusWaleBhaiyaTheme(content = content)
}

