package com.arshia.podcast.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.arshia.podcast.app.app.rememberPodcastAppState
import com.arshia.podcast.app.navigation.PodcastNavigation
import com.arshia.podcast.core.designsystem.theme.PodcastTheme
import com.arshia.podcast.core.network.util.NetworkMonitor
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
                else -> false
            }
        }

        enableEdgeToEdge()
        setContent {
            val appState = rememberPodcastAppState(networkMonitor)
            KoinAndroidContext {
                if (uiState !is MainActivityUiState.Loading) {
                    PodcastTheme(
                        theme = try {
                            (uiState as MainActivityUiState.Authorized).data.theme
                        } catch (e: Exception) {
                            (uiState as MainActivityUiState.UnAuthorized).data.theme
                        }
                    ) {
                        PodcastNavigation(
                            appState = appState,
                            uiState = uiState
                        )
                    }
                }
            }
        }
    }
}
