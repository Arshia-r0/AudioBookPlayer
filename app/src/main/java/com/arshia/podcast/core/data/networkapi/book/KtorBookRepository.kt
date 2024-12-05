package com.arshia.podcast.core.data.networkapi.book

import com.arshia.podcast.core.common.Resource
import com.arshia.podcast.core.model.AuthError
import com.arshia.podcast.core.model.AuthToken
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

    override suspend fun getBooks(token: AuthToken): Flow<Resource<BookResponse>> = flow {
        emit(Resource.Loading())
        val response = networkApi.getBooks(token)
        if (response.status != HttpStatusCode.OK)
            emit(Resource.Error((response.body() as AuthError).message))
        else emit(Resource.Success(response.body()))
    }

    override suspend fun getBookDetails(
        bookId: Int,
        token: AuthToken
    ): Flow<Resource<BookDetailsResponse>> = flow {
        emit(Resource.Loading())
        val response = networkApi.getBookDetails(bookId, token)
        if (response.status != HttpStatusCode.OK)
            emit(Resource.Error((response.body() as AuthError).message))
        else emit(Resource.Success(response.body()))
    }

}
