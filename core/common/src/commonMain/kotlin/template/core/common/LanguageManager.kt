package template.core.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import template.core.datastore.local.language.LanguageDataStore
import template.core.model.language.Language

object LanguageManager {
    private val _currentLanguage = MutableStateFlow(Language.SYSTEM)
    val currentLanguage: StateFlow<Language> = _currentLanguage.asStateFlow()

    private var dataStore: LanguageDataStore? = null
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private var isInitialized = false

    fun init(languageDataStore: LanguageDataStore) {
        if (isInitialized) return
        isInitialized = true
        dataStore = languageDataStore

        scope.launch {
            languageDataStore.getLanguage.collect { savedLanguage ->
                val finalLanguage = if (savedLanguage == Language.UNKNOWN) Language.SYSTEM else savedLanguage
                _currentLanguage.value = finalLanguage
            }
        }
    }

    fun resetForTesting() {
        isInitialized = false
        dataStore = null
        _currentLanguage.value = Language.SYSTEM
    }

    fun setLanguage(language: Language) {
        _currentLanguage.value = language
        scope.launch {
            dataStore?.setLanguage(language)
        }
    }
}
