package template.core.datastore.local

import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath
import java.io.File

actual fun producePath(fileName: String): Path = File(System.getProperty("user.home"), fileName).absolutePath.toPath()

actual val systemFileSystem: FileSystem = FileSystem.SYSTEM
