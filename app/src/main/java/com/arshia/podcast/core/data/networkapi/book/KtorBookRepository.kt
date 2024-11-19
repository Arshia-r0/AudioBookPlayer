package com.arshia.podcast.core.data.networkapi.book

import com.arshia.podcast.core.common.Resource
import com.arshia.podcast.core.data.networkapi.getSerializedResource
import com.arshia.podcast.core.model.BookDetailsResponse
import com.arshia.podcast.core.model.BookResponse
import com.arshia.podcast.core.network.ktor.NetworkApi
import kotlinx.coroutines.flow.Flow

class KtorBookRepository(
    private val networkApi: NetworkApi
) : BookRepository {

    override suspend fun getBooks(): Flow<Resource<BookResponse>> =
        getSerializedResource {
            networkApi.getBooks()
        }

    override suspend fun getBookDetails(bookId: Int): Flow<Resource<BookDetailsResponse>> =
        getSerializedResource {
            networkApi.getBooks()
        }

}
