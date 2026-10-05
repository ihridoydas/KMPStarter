package template.core.platform

expect object PlatformUtils {
    fun changeLanguage(code: String)
    fun changeTheme(isDark: Boolean)
}
