package com.arshia.podcast.core.data.imp

import com.arshia.podcast.core.data.PlayerStateRepository
import com.arshia.podcast.core.datastore.PodcastDataStore
import com.arshia.podcast.core.model.PlayerData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PlayerStateRepositoryImp(
    private val podcastDataStore: PodcastDataStore,
) : PlayerStateRepository {

    override val playerData: Flow<PlayerData> = podcastDataStore.preferences
        .map {
            PlayerData(
                currentEpisode = it.currentEpisode,
                currentBook = it.currentBook,
                position = it.position,
            )
        }

    override suspend fun setCurrentPlayerState(playerData: PlayerData) =
        podcastDataStore.setCurrentPlayerState(
            currentEpisode = playerData.currentEpisode,
            currentBook = playerData.currentBook,
            position = playerData.position,
        )

}
