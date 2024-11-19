package com.arshia.podcast.feature.di

import com.arshia.podcast.feature.auth.AuthScreenViewModel
import com.arshia.podcast.feature.main.MainScreenViewModel
import com.arshia.podcast.feature.player.PlayerScreenViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {

    viewModelOf(::AuthScreenViewModel)
    viewModelOf(::MainScreenViewModel)
    viewModelOf(::PlayerScreenViewModel)
    
}
