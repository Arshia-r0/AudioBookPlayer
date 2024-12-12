package com.arshia.podcast.core.data.di

import com.arshia.podcast.core.data.AuthRepository
import com.arshia.podcast.core.data.BookRepository
import com.arshia.podcast.core.data.PlayerStateRepository
import com.arshia.podcast.core.data.UserDataRepository
import com.arshia.podcast.core.data.imp.KtorAuthRepository
import com.arshia.podcast.core.data.imp.KtorBookRepository
import com.arshia.podcast.core.data.imp.PlayerStateRepositoryImp
import com.arshia.podcast.core.data.imp.UserDataRepositoryImp
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {

    singleOf(::UserDataRepositoryImp) {
        bind<UserDataRepository>()
    }

    singleOf(::PlayerStateRepositoryImp) {
        bind<PlayerStateRepository>()
    }

    singleOf(::KtorAuthRepository) {
        bind<AuthRepository>()
    }

    singleOf(::KtorBookRepository) {
        bind<BookRepository>()
    }

}
