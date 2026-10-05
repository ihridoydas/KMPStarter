package template.core.platform

import android.os.Build
import android.util.Log
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

actual object RootUtil {
    private val Tag = RootUtil::class.java.simpleName
    private const val PATH_TO_WHICH = "/system/xbin/which"
    private const val CMD_SU = "su"
    private val PATHS_SU = arrayOf(
        "/system/app/Superuser.apk",
        "/system/etc/init.d/99SuperSUDaemon",
        "/dev/com.koushikdutta.superuser.daemon/",
        "/system/xbin/daemonsu",
        "/system/xbin/busybox",
        "/system/xbin/su",
        "/system/sd/xbin/su",
        "/system/bin/su",
        "/system/bin/failsafe/su",
        "/su/bin/su",
        "/sbin/su",
        "/data/local/xbin/su",
        "/data/local/bin/su",
        "/data/local/su",
    )

    actual fun isDeviceRooted(): Boolean {
        return checkRootBuild() || checkRootApps() || checkRootSu()
    }

    private fun checkRootBuild(): Boolean {
        val buildTags = Build.TAGS
        val ret = buildTags != null && buildTags.contains("test-keys")
        if (ret) {
            Log.e(Tag, "Root check is true (Test build or custom build)")
        }
        return ret
    }

    private fun checkRootApps(): Boolean {
        for (path in PATHS_SU) {
            if (File(path).exists()) {
                Log.e(Tag, "Root check is true (SU application is found: $path)")
                return true
            }
        }
        return false
    }

    @Suppress("SwallowedException", "TooGenericExceptionCaught")
    private fun checkRootSu(): Boolean {
        var process: Process? = null
        val ret = try {
            process = Runtime.getRuntime().exec(arrayOf(PATH_TO_WHICH, CMD_SU))
            val inputStream = BufferedReader(InputStreamReader(process.inputStream))
            inputStream.readLine() != null
        } catch (t: Throwable) {
            false
        } finally {
            process?.destroy()
        }
        if (ret) {
            Log.e(Tag, "Root check is true (SU is enabled)")
        }
        return ret
    }
}
