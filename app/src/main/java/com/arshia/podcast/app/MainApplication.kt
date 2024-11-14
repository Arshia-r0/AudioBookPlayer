package com.arshia.podcast.app

import android.app.Application
import com.arshia.podcast.app.di.mainModule
import com.arshia.podcast.core.datastore.di.dataStoreModule
import com.arshia.podcast.core.network.di.networkModule
import com.arshia.podcast.feature.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(
                mainModule,
                viewModelModule,
                networkModule,
                dataStoreModule,
            )
        }
    }

}