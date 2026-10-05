/*
* MIT License
*
* Copyright (c) 2026 Hridoy Chandra Das
*
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:
*
* The above copyright notice and this permission notice shall be included in all
* copies or substantial portions of the Software.
*
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
* SOFTWARE.
*
*/
package template

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import kotlinx.coroutines.flow.flowOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.compose.KoinContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import template.feature.home.screens.HomeScreen
import template.navigation.api.Navigator
import template.navigation.api.ScreenDestinations
import template.core.datastore.local.theme.ThemeLocalDataStore
import template.core.datastore.local.theme.ThemeMode

class HomeScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        val mockThemeDataStore = object : ThemeLocalDataStore {
            override val themeMode = flowOf(ThemeMode.LIGHT)

            override suspend fun setThemeMode(mode: ThemeMode) {}
        }

        stopKoin()
        startKoin {
            modules(
                module {
                    single<ThemeLocalDataStore> { mockThemeDataStore }
                },
            )
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun testHomeScreenButton() {
        val mockNavigator = object : Navigator {
            var navigatedTo: ScreenDestinations? = null

            override fun navigate(route: ScreenDestinations) {
                navigatedTo = route
            }

            override fun goBack() {}
        }

        composeTestRule.setContent {
            KoinContext {
                HomeScreen(navigator = mockNavigator)
            }
        }

        composeTestRule.onNodeWithText("Lets Start!").performClick()

        assert(mockNavigator.navigatedTo == ScreenDestinations.ViewScreen)
    }
}
