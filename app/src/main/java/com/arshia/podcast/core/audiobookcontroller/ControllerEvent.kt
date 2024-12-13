package com.arshia.podcast.core.audiobookcontroller

import com.arshia.podcast.core.model.Book
import com.arshia.podcast.core.model.Episode

sealed interface ControllerEvent {
    data class Start(val episode: Episode, val book: Book) : ControllerEvent
    data object Play : ControllerEvent
    data object Pause : ControllerEvent
    data object Next : ControllerEvent
    data object Previous : ControllerEvent
    data class Seek(val position: Long) : ControllerEvent
}
