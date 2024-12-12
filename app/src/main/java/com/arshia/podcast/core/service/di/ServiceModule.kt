package com.arshia.podcast.core.service.di

import com.arshia.podcast.core.service.AudioBookSessionService
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val serviceModule = module {

    singleOf(::AudioBookSessionService)

}