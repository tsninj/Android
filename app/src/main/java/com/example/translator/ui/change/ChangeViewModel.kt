package com.example.translator.ui.change

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.translator.data.Word
import com.example.translator.data.WordRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChangeViewModel(private val repository: WordRepository) : ViewModel() {
    private val _firstWord = MutableStateFlow("")
    val firstWord = _firstWord.asStateFlow()

    private val _secondWord = MutableStateFlow("")
    val secondWord = _secondWord.asStateFlow()

    fun onFirstWordChange(newValue: String) {
        _firstWord.value = newValue
    }

    fun onSecondWordChange(newValue: String) {
        _secondWord.value = newValue
    }

    fun insertTranslation() {
        val word = Word(english = _firstWord.value, mongolia = _secondWord.value)
        viewModelScope.launch {
            repository.insert(word)
        }
    }

    fun cancelTranslation() {
        _firstWord.value = ""
        _secondWord.value = ""
    }
}
