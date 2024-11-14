package com.arshia.podcast.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import com.arshia.podcast.core.model.AppTheme

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

@Composable
fun PodcastTheme(
    theme: AppTheme,
    content: @Composable () -> Unit
) {
    val colorScheme = when(theme) {
        AppTheme.System -> if(isSystemInDarkTheme()) DarkColorScheme else LightColorScheme
        AppTheme.Dark -> DarkColorScheme
        AppTheme.Light -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}