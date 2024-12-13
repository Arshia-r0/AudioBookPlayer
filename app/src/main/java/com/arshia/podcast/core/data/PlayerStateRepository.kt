package com.arshia.podcast.core.data

import com.arshia.podcast.core.model.PlayerState
import kotlinx.coroutines.flow.Flow

interface PlayerStateRepository {

    val playerState: Flow<PlayerState>

    suspend fun setCurrentPlayerState(playerState: PlayerState)

}