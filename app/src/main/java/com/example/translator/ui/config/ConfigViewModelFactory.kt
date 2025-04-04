package com.example.translator.ui.config

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.translator.data.ConfigRepository

class ConfigViewModelFactory(
    private val configRepository: ConfigRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConfigViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ConfigViewModel(configRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class1")
    }
}
