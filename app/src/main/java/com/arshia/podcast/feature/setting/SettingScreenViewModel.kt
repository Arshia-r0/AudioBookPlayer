package com.arshia.podcast.feature.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arshia.podcast.core.common.util.next
import com.arshia.podcast.core.data.UserDataRepository
import com.arshia.podcast.core.model.AppTheme
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingScreenViewModel(
    private val userDataRepository: UserDataRepository,
) : ViewModel() {

    val appTheme = userDataRepository.userData
        .map { it.theme }
        .stateIn(
            scope = viewModelScope,
            initialValue = AppTheme.System,
            started = SharingStarted.WhileSubscribed(5000),
        )

    fun changeAppTheme() {
        viewModelScope.launch {
            userDataRepository.setAppTheme(appTheme.value.next())
        }
    }

}