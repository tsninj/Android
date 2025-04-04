package com.example.translator.ui.navigation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.translator.data.Word
import com.example.translator.data.WordRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NavViewModel(val repository: WordRepository) : ViewModel() {
    private val _editingWord = mutableStateOf<Word?>(null)
    val editingWord: Word? get() = _editingWord.value

    val words: StateFlow<List<Word>> = repository.getAllWords
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun set(word: Word?) {
        _editingWord.value = word
    }

    fun add(word: Word) {
        viewModelScope.launch {
            repository.insert(word)
        }
    }

    fun update(word: Word) {
        viewModelScope.launch {
            repository.update(word)
        }
    }

    fun delete(word: Word) {
        viewModelScope.launch {
            repository.delete(word)
        }
    }
}