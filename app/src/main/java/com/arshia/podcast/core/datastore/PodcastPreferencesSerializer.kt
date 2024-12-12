package com.arshia.podcast.core.datastore

import android.util.Log
import androidx.datastore.core.Serializer
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream


class PodcastPreferencesSerializer : Serializer<PodcastPreferences> {

    override val defaultValue: PodcastPreferences = PodcastPreferences()

    override suspend fun readFrom(input: InputStream): PodcastPreferences {
        return try {
            Json.decodeFromString(
                deserializer = PodcastPreferences.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: Exception) {
            defaultValue.also {
                Log.e("readUserData", e.localizedMessage ?: "error")
            }
        }
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun writeTo(t: PodcastPreferences, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = PodcastPreferences.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }

}