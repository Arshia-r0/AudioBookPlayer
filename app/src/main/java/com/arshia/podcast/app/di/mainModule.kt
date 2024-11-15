package com.arshia.podcast.app.di

import com.arshia.podcast.app.MainActivityViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val mainModule = module {

viewModelOf(::MainActivityViewModel)

}
