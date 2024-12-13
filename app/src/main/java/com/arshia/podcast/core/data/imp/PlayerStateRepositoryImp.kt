package com.arshia.podcast.core.data.imp

import com.arshia.podcast.core.data.PlayerStateRepository
import com.arshia.podcast.core.datastore.PodcastDataStore
import com.arshia.podcast.core.model.PlayerState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PlayerStateRepositoryImp(
    private val podcastDataStore: PodcastDataStore,
) : PlayerStateRepository {

    override val playerState: Flow<PlayerState> = podcastDataStore.preferences
        .map {
            PlayerState(
                currentEpisode = it.currentEpisode,
                currentBook = it.currentBook,
                position = it.position,
            )
        }

    override suspend fun setCurrentPlayerState(playerState: PlayerState) =
        podcastDataStore.setCurrentPlayerState(
            currentEpisode = playerState.currentEpisode,
            currentBook = playerState.currentBook,
            position = playerState.position,
        )

}
