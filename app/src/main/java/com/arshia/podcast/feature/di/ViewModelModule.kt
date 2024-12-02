package com.arshia.podcast.feature.di

import com.arshia.podcast.feature.login.LoginScreenViewModel
import com.arshia.podcast.feature.main.MainScreenViewModel
import com.arshia.podcast.feature.player.PlayerScreenViewModel
import com.arshia.podcast.feature.register.RegisterScreenViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {

    viewModelOf(::RegisterScreenViewModel)
    viewModelOf(::LoginScreenViewModel)
    viewModelOf(::MainScreenViewModel)
    viewModelOf(::PlayerScreenViewModel)
    
}
