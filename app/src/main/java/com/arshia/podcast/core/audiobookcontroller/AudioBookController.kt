package com.arshia.podcast.core.audiobookcontroller

import android.content.ComponentName
import android.content.Context
import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.arshia.podcast.core.common.BASE_URL
import com.arshia.podcast.core.model.Book
import com.arshia.podcast.core.model.Episode
import com.arshia.podcast.core.service.AudioBookSessionService


class AudioBookController(
    context: Context,
) {

    private val sessionToken =
        SessionToken(context, ComponentName(context, AudioBookSessionService::class.java))
    private var controllerFuture = MediaController.Builder(context, sessionToken).buildAsync()
    private val mediaController: MediaController?
        get() = if (controllerFuture.isDone) controllerFuture.get()
        else null

    fun release() = MediaController.releaseFuture(controllerFuture)

    inner class Command {

        val start: (Episode, Book, Int, Int) -> Unit = { episode, book, start, count ->
            mediaController?.apply {
                repeat(count) {
                    setMediaItem(MediaItem.fromUri(Uri.parse("$BASE_URL/book/${book.bookId}/${start}")))
                }
                repeatMode = Player.REPEAT_MODE_ALL
                prepare()
                repeat(start) { seekToNext() }
                addListener(object : Player.Listener {
                    override fun onPlayerError(error: PlaybackException) {}
                })
                play()
            }
        }
        val play = { mediaController?.play() }
        val pause = { mediaController?.pause() }
        val next = { mediaController?.seekToNext() }
        val previous = { mediaController?.seekToPrevious() }
        val seek: (Long) -> Unit = { position -> mediaController?.seekTo(position) }

    }

}
