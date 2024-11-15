package com.arshia.podcast.core.datastore.di

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.arshia.podcast.core.datastore.PodcastDataStore
import com.arshia.podcast.core.datastore.UserDataSerializer
import com.arshia.podcast.core.model.UserData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataStoreModule = module {

    single<DataStore<UserData>> {
        DataStoreFactory.create(
            serializer = UserDataSerializer(),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { androidContext().dataStoreFile("podcast.pb") }
        )
    }

    singleOf(::PodcastDataStore)
    singleOf(::UserDataSerializer)

}
