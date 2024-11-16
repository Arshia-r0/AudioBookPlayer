package com.arshia.podcast.core.data.networkapi.book

import com.arshia.podcast.core.model.Book
import com.arshia.podcast.core.model.Episode
import com.arshia.podcast.core.network.ktor.NetworkApi

class KtorBookRepository(
    private val networkApi: NetworkApi
) : BookRepository {

    override suspend fun getBooks(): Map<BookId, Book> =
        networkApi.getBooks()

    override suspend fun getEpisodes(bookId: Int): List<Episode> =
        networkApi.getBookDetails()

}
