package template.core.model.language

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Language {
    @SerialName("unknown")
    UNKNOWN,

    @SerialName("system")
    SYSTEM,

    @SerialName("english")
    ENGLISH,

    @SerialName("japanese")
    JAPANESE,

    @SerialName("bengali")
    BENGALI,
}

@Serializable
data class LanguageState(val language: Language = Language.UNKNOWN)
