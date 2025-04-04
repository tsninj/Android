package com.example.translator.ui.change

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.translator.data.Word

class ChangeViewModelFactory(private val word: Word?) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChangeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChangeViewModel(word) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
