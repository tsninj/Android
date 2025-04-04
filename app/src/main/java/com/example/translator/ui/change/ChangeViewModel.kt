package com.example.translator.ui.change

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.translator.data.Word

class ChangeViewModel(word: Word?) : ViewModel() {

    private val _firstWord = MutableStateFlow(word?.english ?: "")
    val firstWord: StateFlow<String> = _firstWord

    private val _secondWord = MutableStateFlow(word?.mongolia ?: "")
    val secondWord: StateFlow<String> = _secondWord

    fun updateFirstWord(newWord: String?) {
        if (!newWord.isNullOrBlank()) {
            _firstWord.value = newWord
        }
    }

    fun updateSecondWord(newWord: String?) {
        if (!newWord.isNullOrBlank()) {
            _secondWord.value = newWord
        }
    }
}
