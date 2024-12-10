package jp.speakbuddy.edisonandroidexercise.local.data

import androidx.datastore.core.Serializer
import jp.speakbuddy.edisonandroidexercise.common.DispatcherProvider
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

class FactEntitySerializer @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
): Serializer<FactEntity> {
    override val defaultValue: FactEntity
        get() = FactEntity()

    override suspend fun readFrom(input: InputStream): FactEntity {
        return try {
            Json.decodeFromString(
                deserializer = FactEntity.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: FactEntity, output: OutputStream) {
        withContext(dispatcherProvider.io) {
            output.write(
                Json.encodeToString(
                    serializer = FactEntity.serializer(),
                    value = t,
                ).encodeToByteArray()
            )
        }
    }
}