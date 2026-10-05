package template.core.datastore.local.theme

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import template.core.model.theme.ThemeMode
import template.core.model.theme.ThemeState

interface ThemeLocalDataStore {
    val themeMode: Flow<ThemeMode>
    suspend fun setThemeMode(mode: ThemeMode)
}

class ThemeLocalDataStoreImpl(private val dataStore: DataStore<ThemeState>) : ThemeLocalDataStore {
    override val themeMode: Flow<ThemeMode> =
        dataStore.data.map { it.themeMode }

    override suspend fun setThemeMode(mode: ThemeMode) {
        dataStore.updateData { current ->
            current.copy(themeMode = mode)
        }
    }
}
