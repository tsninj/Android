package com.example.translator.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.translator.data.Word
import com.example.translator.data.WordRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(private val wordRepository: WordRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    init {
        loadWords()
    }

    private fun loadWords() {
        viewModelScope.launch {
            wordRepository.getAllWords().collect { words ->
                _uiState.update { it.copy(
                    words = words,
                    currentIndex = if (words.isNotEmpty()) 0 else -1,
                    currentWord = words.getOrNull(0),
                    hasWords = words.isNotEmpty()
                ) }
            }
        }
    }

    fun showPreviousWord() {
        val index = (_uiState.value.currentIndex - 1).coerceAtLeast(0)
        _uiState.update {
            it.copy(
                currentIndex = index,
                currentWord = it.words.getOrNull(index)
            )
        }
    }

    fun showNextWord() {
        val index = (_uiState.value.currentIndex + 1).coerceAtMost(_uiState.value.words.lastIndex)
        _uiState.update {
            it.copy(
                currentIndex = index,
                currentWord = it.words.getOrNull(index)
            )
        }
    }

    fun toggleTranslationVisibility() {
        _uiState.update { it.copy(shouldShowTranslation = !it.shouldShowTranslation) }
    }

    fun confirmDelete() {
        _uiState.update { it.copy(showDeleteDialog = true) }
    }

    fun dismissDeleteDialog() {
        _uiState.update { it.copy(showDeleteDialog = false) }
    }

    fun deleteCurrentWord() {
        val word = _uiState.value.currentWord ?: return
        viewModelScope.launch {
            wordRepository.delete(word)
            dismissDeleteDialog()
        }
    }
}

data class UiState(
    val words: List<Word> = emptyList(),
    val currentWord: Word? = null,
    val currentIndex: Int = -1,
    val shouldShowTranslation: Boolean = true,
    val showDeleteDialog: Boolean = false,
    val hasWords: Boolean = false
)