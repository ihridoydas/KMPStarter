package template.core.datastore.local.language

import androidx.datastore.core.okio.OkioSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okio.BufferedSink
import okio.BufferedSource
import template.core.model.language.LanguageState

object LanguageSerializer : OkioSerializer<LanguageState> {
    override val defaultValue: LanguageState = LanguageState()

    override suspend fun readFrom(source: BufferedSource): LanguageState = try {
        Json.decodeFromString<LanguageState>(source.readUtf8())
    } catch (
        @Suppress("SwallowedException") e: SerializationException,
    ) {
        defaultValue
    } catch (
        @Suppress("SwallowedException") e: IllegalArgumentException,
    ) {
        defaultValue
    }

    override suspend fun writeTo(t: LanguageState, sink: BufferedSink) {
        sink.writeUtf8(Json.encodeToString(t))
    }
}
