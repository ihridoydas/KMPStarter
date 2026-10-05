package template.core.model.theme

import kotlinx.serialization.Serializable

@Serializable
enum class ThemeMode {
    LIGHT,
    DARK,
    SYSTEM,
}

@Serializable
data class ThemeState(val themeMode: ThemeMode = ThemeMode.SYSTEM)
