@file:Suppress("INVISIBLE_MEMBER", "INVISIBLE_REFERENCE")
@file:OptIn(org.jetbrains.compose.resources.InternalResourceApi::class)

package template.core.designsystem.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalProvidableLocaleList
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.intl.LocaleList
import org.jetbrains.compose.resources.ComposeEnvironment
import org.jetbrains.compose.resources.LanguageQualifier
import org.jetbrains.compose.resources.LocalComposeEnvironment
import org.jetbrains.compose.resources.ResourceEnvironment
import org.jetbrains.compose.resources.ThemeQualifier
import org.jetbrains.compose.resources.getSystemResourceEnvironment

@Composable
fun ProvideAppLocale(languageCode: String, isDarkTheme: Boolean, content: @Composable () -> Unit) {
    val systemEnv = getSystemResourceEnvironment()
    val customEnv = remember(languageCode, isDarkTheme, systemEnv) {
        ResourceEnvironment(
            language = if (languageCode.isEmpty()) systemEnv.language else LanguageQualifier(languageCode),
            script = systemEnv.script,
            region = systemEnv.region,
            theme = if (isDarkTheme) ThemeQualifier.DARK else ThemeQualifier.LIGHT,
            density = systemEnv.density,
        )
    }

    val customComposeEnvironment = remember(customEnv) {
        object : ComposeEnvironment {
            @Composable
            override fun rememberEnvironment(): ResourceEnvironment {
                return customEnv
            }
        }
    }

    val localeList = remember(languageCode) {
        if (languageCode.isEmpty()) LocaleList.current else LocaleList(Locale(languageCode))
    }

    CompositionLocalProvider(
        LocalComposeEnvironment provides customComposeEnvironment,
        LocalProvidableLocaleList provides localeList,
    ) {
        content()
    }
}
