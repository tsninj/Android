package com.example.translator.ui.config

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.translator.data.ConfigRepository
import com.example.translator.data.Option
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ConfigViewModel(
    private val configRepository: ConfigRepository
) : ViewModel() {

    val configLayout: StateFlow<Option> = configRepository.configLayout
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Option.BOTH)

    fun saveOption(option: Option) {
        viewModelScope.launch {
            configRepository.saveSettingPreference(option)
        }
    }
}
