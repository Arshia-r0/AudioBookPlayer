package com.arshia.podcast.feature.di

import com.arshia.podcast.feature.auth.login.LoginScreenViewModel
import com.arshia.podcast.feature.auth.register.RegisterScreenViewModel
import com.arshia.podcast.feature.main.MainScreenViewModel
import com.arshia.podcast.feature.main.book.BookScreenViewModel
import com.arshia.podcast.feature.main.episode.EpisodeScreenViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {

    viewModelOf(::RegisterScreenViewModel)
    viewModelOf(::LoginScreenViewModel)
    viewModelOf(::BookScreenViewModel)
    viewModelOf(::EpisodeScreenViewModel)
    viewModelOf(::MainScreenViewModel)
    
}
