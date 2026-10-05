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
package template.navigation.impl

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import org.koin.compose.KoinContext
import org.koin.compose.koinInject
import template.core.common.LanguageManager
import template.core.designsystem.util.ProvideAppLocale
import template.core.datastore.StorageComponent
import template.core.model.language.Language
import template.core.datastore.local.language.LanguageDataStore
import template.core.datastore.local.theme.ThemeLocalDataStore
import template.core.model.theme.ThemeMode
import template.core.designsystem.TemplateTheme
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import org.koin.core.module.dsl.singleOf
import template.core.network.createHttpClient
import template.core.network.ApiService
import template.feature.auth.data.repository.AuthRepositoryImpl
import template.feature.auth.domain.repository.AuthRepository
import template.feature.auth.domain.usecase.LoginUseCase
import template.feature.auth.presentation.AuthViewModel
import template.feature.auth.presentation.SplashViewModel
import template.feature.home.data.repository.HomeRepositoryImpl
import template.feature.home.domain.repository.HomeRepository
import template.feature.home.domain.usecase.GetHomeDataUseCase
import template.feature.home.presentation.HomeViewModel
import template.feature.profile.ProfileViewModel
import template.feature.settings.SettingsViewModel

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(appModule)
}

fun doInitKoin() = initKoin {}

val coreNetworkModule = module {
    single { createHttpClient() }
    singleOf(::ApiService)
}

val coreDatastoreModule = module {
    single { StorageComponent.createLanguageDataStore() }
    single { StorageComponent.createThemeDataStore() }
}

val featureSplashModule = module {
    singleOf(::SplashViewModel)
}

val homeModule = module {
    single<HomeRepository> { HomeRepositoryImpl() }
    single { GetHomeDataUseCase(get()) }
    singleOf(::HomeViewModel)
}

val authModule = module {
    single<AuthRepository> { AuthRepositoryImpl() }
    single { LoginUseCase(get()) }
    singleOf(::AuthViewModel)
}

val profileModule = module {
    singleOf(::ProfileViewModel)
}

val settingsModule = module {
    singleOf(::SettingsViewModel)
}

val appModule = module {
    includes(coreNetworkModule, coreDatastoreModule, featureSplashModule, homeModule, authModule, profileModule, settingsModule)
}

@Composable
fun App(onLanguageChange: (String) -> Unit = {}, onThemeChange: (Boolean) -> Unit = {}) {
    KoinContext {
        val languageDataStore: LanguageDataStore = koinInject()
        val themeLocalDataStore: ThemeLocalDataStore = koinInject()

        SideEffect {
            LanguageManager.init(languageDataStore)
        }

        val languageState by LanguageManager.currentLanguage.collectAsState()
        val themeMode by themeLocalDataStore.themeMode.collectAsState(initial = ThemeMode.SYSTEM)

        val languageCode = remember(languageState) {
            when (languageState) {
                Language.ENGLISH -> "en"
                Language.JAPANESE -> "ja"
                Language.BENGALI -> "bn"
                Language.SYSTEM -> ""
                else -> ""
            }
        }

        val isDarkTheme = when (themeMode) {
            ThemeMode.DARK -> true
            ThemeMode.LIGHT -> false
            else -> isSystemInDarkTheme()
        }

        LaunchedEffect(languageCode) {
            onLanguageChange(languageCode)
        }

        LaunchedEffect(isDarkTheme) {
            onThemeChange(isDarkTheme)
        }

        ProvideAppLocale(languageCode, isDarkTheme) {
            TemplateTheme(useDarkTheme = isDarkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    key(languageCode) {
                        MainAnimationNavHost()
                    }
                }
            }
        }
    }
}
