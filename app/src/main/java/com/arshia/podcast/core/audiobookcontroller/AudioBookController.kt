package com.arshia.podcast.core.audiobookcontroller

import android.content.ComponentName
import android.content.Context
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.arshia.podcast.core.common.BASE_URL
import com.arshia.podcast.core.model.Book
import com.arshia.podcast.core.service.AudioBookSessionService
import com.arshia.podcast.feature.main.PlayerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow


class AudioBookController(
    context: Context,
) {

    private val sessionToken =
        SessionToken(context, ComponentName(context, AudioBookSessionService::class.java))
    private var controllerFuture = MediaController.Builder(context, sessionToken).buildAsync()
    private val mediaController: MediaController?
        get() = if (controllerFuture.isDone) controllerFuture.get() else null

    val playerState = mutableStateOf(PlayerState())
    val playerTime = flow {
        while (true) {
            if (mediaController?.isCommandAvailable(Player.COMMAND_GET_CURRENT_MEDIA_ITEM) == true)
                emit((mediaController?.currentPosition?.div(1000))?.toInt() ?: 0)
            delay(1000)
        }
    }

    fun release() = MediaController.releaseFuture(controllerFuture)

    inner class Command {

        fun start(book: Book, start: Int) {
            mediaController?.apply {
                clearMediaItems()
                val uri = Uri.parse("$BASE_URL/book/${book.bookId}/$start")
                setMediaItem(MediaItem.fromUri(uri))
                addListener(object : Player.Listener {

                    override fun onPlayerError(error: PlaybackException) {}

                    override fun onIsPlayingChanged(isPlaying: Boolean) {
                        super.onIsPlayingChanged(isPlaying)
                        playerState.value = playerState.value.copy(isPlaying = isPlaying)
                    }

                })
                prepare()
                play()
            }
        }

        fun play() = mediaController?.play()
        fun pause() = mediaController?.pause()
        fun next() = mediaController?.seekToNext()
        fun previous() = mediaController?.seekToPrevious()
        fun seek(position: Long) = mediaController?.seekTo(position)

    }

}
