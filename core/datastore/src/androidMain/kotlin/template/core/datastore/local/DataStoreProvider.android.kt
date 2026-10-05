package template.core.datastore.local

import android.content.Context
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath

@Volatile
private var internalDataStoreContext: Context? = null

var dataStoreContext: Context
    get() = internalDataStoreContext ?: throw IllegalStateException("dataStoreContext not initialized")
    set(value) {
        internalDataStoreContext = value
    }

actual fun producePath(fileName: String): Path = dataStoreContext.filesDir.resolve(fileName).absolutePath.toPath()

actual val systemFileSystem: FileSystem = FileSystem.SYSTEM
