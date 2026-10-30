package com.example.loopstep.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryLime,
    onPrimary = OnPrimary,
    primaryContainer = BgCard,
    onPrimaryContainer = PrimaryLime,
    secondary = SignalCyan,
    onSecondary = OnPrimary,
    background = BgDark,
    onBackground = TextMain,
    surface = BgSurface,
    onSurface = TextMain,
    surfaceVariant = BgCard,
    onSurfaceVariant = TextMuted,
    outline = BorderDim,
    error = SignalRed
)

@Composable
fun LoopStepTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        content = content
    )
}
