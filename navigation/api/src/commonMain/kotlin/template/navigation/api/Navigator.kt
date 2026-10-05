package template.navigation.api

import kotlinx.serialization.Serializable

interface Navigator {
    fun navigate(route: ScreenDestinations)
    fun goBack()
}

@Serializable
sealed interface ScreenDestinations {
    @Serializable
    data object HomeScreen : ScreenDestinations

    @Serializable
    data object ViewScreen : ScreenDestinations

    @Serializable
    data object ProfileScreen : ScreenDestinations

    @Serializable
    data object SettingsScreen : ScreenDestinations
}
