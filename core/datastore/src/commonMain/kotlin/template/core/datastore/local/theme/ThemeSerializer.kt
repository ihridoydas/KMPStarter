package template.core.datastore.local.theme

import androidx.datastore.core.okio.OkioSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okio.BufferedSink
import okio.BufferedSource
import template.core.model.theme.ThemeState

object ThemeSerializer : OkioSerializer<ThemeState> {
    override val defaultValue: ThemeState = ThemeState()

    override suspend fun readFrom(source: BufferedSource): ThemeState = try {
        Json.decodeFromString<ThemeState>(source.readUtf8())
    } catch (
        @Suppress("SwallowedException") e: SerializationException,
    ) {
        defaultValue
    } catch (
        @Suppress("SwallowedException") e: IllegalArgumentException,
    ) {
        defaultValue
    }

    override suspend fun writeTo(t: ThemeState, sink: BufferedSink) {
        sink.writeUtf8(Json.encodeToString(t))
    }
}
