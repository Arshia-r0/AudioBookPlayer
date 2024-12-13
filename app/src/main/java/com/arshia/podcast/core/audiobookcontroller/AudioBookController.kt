package com.arshia.podcast.core.audiobookcontroller

import android.content.ComponentName
import android.content.Context
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.arshia.podcast.core.data.PlayerStateRepository
import com.arshia.podcast.core.model.Book
import com.arshia.podcast.core.model.Episode
import com.arshia.podcast.core.model.PlayerState
import com.arshia.podcast.core.service.AudioBookSessionService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class AudioBookController(
    context: Context,
    playerStateRepository: PlayerStateRepository,
    scope: CoroutineScope,
) {

    private val sessionToken =
        SessionToken(context, ComponentName(context, AudioBookSessionService::class.java))
    private var controllerFuture = MediaController.Builder(context, sessionToken).buildAsync()
    private val mediaController: MediaController?
        get() = if (controllerFuture.isDone) controllerFuture.get()
        else null
    private val playerState = playerStateRepository.playerState
        .stateIn(
            scope = scope,
            initialValue = PlayerState(),
            started = SharingStarted.WhileSubscribed(5000)
        )

    init {
        mediaController?.repeatMode = Player.REPEAT_MODE_ALL
    }

    fun release() = MediaController.releaseFuture(controllerFuture)

    inner class Command {

        val start: (Episode, Book) -> Unit = { episode, book ->

        }
        val play = { mediaController?.play() }
        val pause = { mediaController?.pause() }
        val next = { mediaController?.seekToNext() }
        val previous = { mediaController?.seekToPrevious() }
        val seek: (Long) -> Unit = { position -> mediaController?.seekTo(position) }

    }

}
