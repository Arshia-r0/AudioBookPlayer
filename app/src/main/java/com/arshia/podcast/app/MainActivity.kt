package com.arshia.podcast.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.arshia.podcast.core.designsystem.theme.PodcastTheme
import com.arshia.podcast.core.network.NetworkMonitor
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.compose.KoinAndroidContext

class MainActivity : ComponentActivity() {

    private val networkMonitor by inject<NetworkMonitor>()

    private val viewModel by inject<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        var uiState: MainActivityUiState by mutableStateOf(MainActivityUiState.Loading)
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.onEach {
                    uiState = it
                }.collect {}
            }
        }
        splashScreen.setKeepOnScreenCondition {
            when(uiState) {
                MainActivityUiState.Loading -> true
                is MainActivityUiState.Success -> false
            }
        }

        enableEdgeToEdge()
        setContent {
            KoinAndroidContext {
                if(uiState is MainActivityUiState.Success) {
                    uiState = uiState as MainActivityUiState.Success
                    PodcastTheme(
                        theme = (uiState as MainActivityUiState.Success).data.theme
                    ) {
                        Text("Hello")
                    }
                }
            }
        }
    }
}
