package com.arshia.podcast.core.data

import android.content.ComponentName
import android.content.Context
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.arshia.podcast.core.service.AudioBookSessionService
import com.google.common.util.concurrent.MoreExecutors

class AudioBookController(
    context: Context,
    private val playerStateRepository: PlayerStateRepository
) {

    private val sessionToken = SessionToken(
        context,
        ComponentName(context, AudioBookSessionService::class.java)
    )

    private val controllerFuture = MediaController.Builder(context, sessionToken).buildAsync()

    init {
        controllerFuture.addListener({

        }, MoreExecutors.directExecutor())
    }

}
