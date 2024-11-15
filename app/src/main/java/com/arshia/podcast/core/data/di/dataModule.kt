package com.arshia.podcast.core.data.di

import com.arshia.podcast.core.data.UserDataRepository
import com.arshia.podcast.core.data.UserDataRepositoryImp
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {

    singleOf(::UserDataRepositoryImp) {
        bind<UserDataRepository>()
    }

} 
