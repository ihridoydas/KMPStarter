package template.core.datastore

import template.core.datastore.local.createDataStore
import template.core.datastore.local.DATASTORE_FILE_NAME_LANGUAGE
import template.core.datastore.local.DATASTORE_FILE_NAME_THEME
import template.core.datastore.local.language.LanguageDataStore
import template.core.datastore.local.language.LanguageDataStoreImpl
import template.core.datastore.local.language.LanguageSerializer
import template.core.datastore.local.theme.ThemeLocalDataStore
import template.core.datastore.local.theme.ThemeLocalDataStoreImpl
import template.core.datastore.local.theme.ThemeSerializer

object StorageComponent {
    fun createLanguageDataStore(): LanguageDataStore = LanguageDataStoreImpl(
        createDataStore(
            producePath = { template.core.datastore.local.producePath(DATASTORE_FILE_NAME_LANGUAGE) },
            serializer = LanguageSerializer,
        ),
    )

    fun createThemeDataStore(): ThemeLocalDataStore = ThemeLocalDataStoreImpl(
        createDataStore(
            producePath = { template.core.datastore.local.producePath(DATASTORE_FILE_NAME_THEME) },
            serializer = ThemeSerializer,
        ),
    )
}
