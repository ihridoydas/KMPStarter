package template.core.designsystem

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalProvidableLocaleList
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import template.core.designsystem.generated.resources.Res
import template.core.designsystem.generated.resources.noto_sans_bengali
import template.core.designsystem.generated.resources.noto_sans_jp

@Composable
fun getAppFontFamily(): FontFamily {
    val localeList = LocalProvidableLocaleList.current
    val languageCode = localeList.firstOrNull()?.language ?: ""

    val jpFont = Font(Res.font.noto_sans_jp, FontWeight.Normal)
    val bengaliFont = Font(Res.font.noto_sans_bengali, FontWeight.Normal)

    return when (languageCode) {
        "bn" -> FontFamily(bengaliFont, jpFont)
        "ja" -> FontFamily(jpFont, bengaliFont)
        else -> FontFamily(jpFont, bengaliFont)
    }
}

@Composable
fun getTypography(): Typography {
    val fontFamily = getAppFontFamily()
    val defaultTypography = Typography()

    return Typography(
        displayLarge = defaultTypography.displayLarge.copy(fontFamily = fontFamily),
        displayMedium = defaultTypography.displayMedium.copy(fontFamily = fontFamily),
        displaySmall = defaultTypography.displaySmall.copy(fontFamily = fontFamily),
        headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = fontFamily),
        headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = fontFamily),
        headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = fontFamily),
        titleLarge = defaultTypography.titleLarge.copy(fontFamily = fontFamily),
        titleMedium = defaultTypography.titleMedium.copy(fontFamily = fontFamily),
        titleSmall = defaultTypography.titleSmall.copy(fontFamily = fontFamily),
        bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = fontFamily),
        bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = fontFamily),
        bodySmall = defaultTypography.bodySmall.copy(fontFamily = fontFamily),
        labelLarge = defaultTypography.labelLarge.copy(fontFamily = fontFamily),
        labelMedium = defaultTypography.labelMedium.copy(fontFamily = fontFamily),
        labelSmall = defaultTypography.labelSmall.copy(fontFamily = fontFamily),
    )
}
