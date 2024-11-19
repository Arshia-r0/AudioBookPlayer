package com.arshia.podcast.core.data.networkapi.book

import com.arshia.podcast.core.common.Resource
import com.arshia.podcast.core.model.BookDetailsResponse
import com.arshia.podcast.core.model.BookResponse
import kotlinx.coroutines.flow.Flow


interface BookRepository {

    suspend fun getBooks(): Flow<Resource<BookResponse>>

    suspend fun getBookDetails(bookId: Int): Flow<Resource<BookDetailsResponse>>

}
