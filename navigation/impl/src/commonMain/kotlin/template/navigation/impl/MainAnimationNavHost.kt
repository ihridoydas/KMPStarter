package template.navigation.impl

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import template.core.designsystem.screens.HomeScreen
import template.core.designsystem.screens.ViewScreen
import template.feature.profile.ProfileScreen
import template.feature.settings.SettingsScreen
import template.navigation.api.Navigator
import template.navigation.api.ScreenDestinations
import template.core.designsystem.components.SpatialWrapper

@Composable
fun MainAnimationNavHost() {
    val backStack = remember { mutableStateListOf<ScreenDestinations>(ScreenDestinations.HomeScreen) }

    SpatialWrapper {
        Box(modifier = Modifier.fillMaxSize()) {
            NavDisplay(
                backStack = backStack,
                onBack = {
                    if (backStack.size > 1) {
                        backStack.removeAt(backStack.size - 1)
                    }
                },
            ) { key ->
                when (key) {
                    ScreenDestinations.HomeScreen -> NavEntry(key) {
                        HomeScreen(
                            navigator = object : Navigator {
                                override fun navigate(route: ScreenDestinations) {
                                    backStack.add(route)
                                }

                                override fun goBack() {
                                    if (backStack.size > 1) {
                                        backStack.removeAt(backStack.size - 1)
                                    }
                                }
                            },
                        )
                    }

                    ScreenDestinations.ViewScreen -> NavEntry(key) {
                        ViewScreen(
                            onBackPress = {
                                if (backStack.size > 1) {
                                    backStack.removeAt(backStack.size - 1)
                                }
                            },
                        )
                    }

                    ScreenDestinations.ProfileScreen -> NavEntry(key) {
                        ProfileScreen(
                            navigator = object : Navigator {
                                override fun navigate(route: ScreenDestinations) {
                                    backStack.add(route)
                                }

                                override fun goBack() {
                                    if (backStack.size > 1) {
                                        backStack.removeAt(backStack.size - 1)
                                    }
                                }
                            },
                        )
                    }

                    ScreenDestinations.SettingsScreen -> NavEntry(key) {
                        SettingsScreen(
                            navigator = object : Navigator {
                                override fun navigate(route: ScreenDestinations) {
                                    backStack.add(route)
                                }

                                override fun goBack() {
                                    if (backStack.size > 1) {
                                        backStack.removeAt(backStack.size - 1)
                                    }
                                }
                            },
                        )
                    }
                }
            }
        }
    }
}
