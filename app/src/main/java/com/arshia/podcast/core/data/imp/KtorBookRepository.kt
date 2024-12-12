package com.arshia.podcast.core.data.imp

import com.arshia.podcast.core.common.Resource
import com.arshia.podcast.core.data.BookRepository
import com.arshia.podcast.core.model.AuthError
import com.arshia.podcast.core.model.BookDetailsResponse
import com.arshia.podcast.core.model.BookResponse
import com.arshia.podcast.core.network.ktor.NetworkApi
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class KtorBookRepository(
    private val networkApi: NetworkApi
) : BookRepository {

    override suspend fun getBooks(): Flow<Resource<BookResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = networkApi.getBooks()
            if (response.status != HttpStatusCode.OK)
                emit(Resource.Error((response.body() as AuthError).message))
            else emit(Resource.Success(response.body()))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.localizedMessage ?: "error"))
        }
    }

    override suspend fun getBookDetails(bookId: Int): Flow<Resource<BookDetailsResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = networkApi.getBookDetails(bookId)
            if (response.status != HttpStatusCode.OK)
                emit(Resource.Error((response.body() as AuthError).message))
            else emit(Resource.Success(response.body()))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.localizedMessage ?: "error"))
        }
    }

}
