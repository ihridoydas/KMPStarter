package template.core.datastore.local.language

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import template.core.model.language.Language
import template.core.model.language.LanguageState

interface LanguageDataStore {
    val getLanguage: Flow<Language>
    suspend fun setLanguage(language: Language)
}

class LanguageDataStoreImpl(private val dataStore: DataStore<LanguageState>) : LanguageDataStore {
    override val getLanguage: Flow<Language> =
        dataStore.data.map { it.language }

    override suspend fun setLanguage(language: Language) {
        dataStore.updateData { current ->
            current.copy(language = language)
        }
    }
}
