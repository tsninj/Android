package com.example.translator.ui.home

import androidx.lifecycle.ViewModel
import com.example.translator.data.Word
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel : ViewModel() {
    private val _currentIndex = MutableStateFlow(0)
    val currentIndex: StateFlow<Int> = _currentIndex

    fun nextWord(words: List<Word>) {
        if (words.isNotEmpty()) {
            _currentIndex.update { (it + 1) % words.size }
        }
    }

    fun previousWord(words: List<Word>) {
        if (words.isNotEmpty()) {
            _currentIndex.update {
                if (it > 0) it - 1 else words.size - 1
            }
        }
    }

    fun resetIndex() {
        _currentIndex.value = 0
    }

    fun setIndex(index: Int) {
        _currentIndex.value = index
    }
}
