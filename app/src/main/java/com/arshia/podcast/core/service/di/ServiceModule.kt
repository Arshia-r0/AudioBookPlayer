package com.arshia.podcast.core.service.di

import com.arshia.podcast.core.service.AudioBookSession
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val serviceModule = module {

    singleOf(::AudioBookSession)

}