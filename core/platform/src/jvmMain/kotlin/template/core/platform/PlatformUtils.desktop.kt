package template.core.platform

actual object PlatformUtils {
    actual fun changeLanguage(code: String) {}
    actual fun changeTheme(isDark: Boolean) {}
}

actual object RootUtil {
    actual fun isDeviceRooted(): Boolean = false
}
