package com.arshia.podcast.core.data

import com.arshia.podcast.core.model.PlayerData
import kotlinx.coroutines.flow.Flow

interface PlayerStateRepository {

    val playerData: Flow<PlayerData>

    suspend fun setCurrentPlayerState(playerData: PlayerData)

}