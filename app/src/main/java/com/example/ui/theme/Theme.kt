package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = EmeraldPrimaryFixedDim,
    onPrimary = Color(0xFF00391E),
    primaryContainer = EmeraldPrimaryContainer,
    onPrimaryContainer = EmeraldOnPrimaryContainer,
    secondary = TealSecondaryContainer,
    onSecondary = Color(0xFF003730),
    secondaryContainer = TealSecondary,
    onSecondaryContainer = TealSecondaryContainer,
    tertiary = GoldTertiaryFixed,
    onTertiary = Color(0xFF3D2F00),
    background = Color(0xFF111C24),
    onBackground = Color(0xFFE2E8F0),
    surface = Color(0xFF16232D),
    onSurface = Color(0xFFE2E8F0),
    surfaceVariant = Color(0xFF22323D),
    onSurfaceVariant = Color(0xFFBFC9BF),
    outline = Color(0xFF707A71),
    error = StatusDanger,
    onError = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = EmeraldPrimaryContainer,
    onPrimary = EmeraldOnPrimary,
    primaryContainer = EmeraldPrimary,
    onPrimaryContainer = EmeraldOnPrimaryContainer,
    secondary = TealSecondary,
    onSecondary = Color.White,
    secondaryContainer = TealSecondaryContainer,
    onSecondaryContainer = TealOnSecondaryContainer,
    tertiary = GoldTertiary,
    onTertiary = Color.White,
    tertiaryContainer = GoldTertiaryContainer,
    onTertiaryContainer = Color(0xFF4F3E00),
    background = SurfaceCanvas,
    onBackground = OnSurface,
    surface = SurfaceBackground,
    onSurface = OnSurface,
    surfaceVariant = SurfaceSubtle,
    onSurfaceVariant = OnSurfaceVariant,
    outline = BorderSubtle,
    error = StatusDanger,
    onError = Color.White,
    errorContainer = ErrorContainer,
    onErrorContainer = OnErrorContainer
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Keep consistent Pesantren emerald & gold branding
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
