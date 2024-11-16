package com.arshia.podcast.core.data.book

import com.arshia.podcast.core.model.Book
import com.arshia.podcast.core.model.Episode

typealias BookId = Int

interface BookRepository {

    suspend fun getBooks(): Map<BookId, Book>

    suspend fun getEpisodes(bookId: Int): List<Episode>

}
