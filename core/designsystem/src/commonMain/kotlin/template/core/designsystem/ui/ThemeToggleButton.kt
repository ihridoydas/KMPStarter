package template.core.designsystem.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import template.core.model.theme.ThemeMode

@Composable
fun ThemeToggleButton(themeMode: ThemeMode, onToggle: (ThemeMode) -> Unit, modifier: Modifier = Modifier) {
    val isDark = when (themeMode) {
        ThemeMode.DARK -> true
        ThemeMode.LIGHT -> false
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
    }

    IconButton(
        modifier = modifier,
        onClick = {
            val newMode = if (isDark) ThemeMode.LIGHT else ThemeMode.DARK
            onToggle(newMode)
        },
    ) {
        Icon(
            imageVector =
            if (isDark) {
                Icons.Default.DarkMode
            } else {
                Icons.Default.LightMode
            },
            contentDescription = "Toggle Theme",
        )
    }
}
