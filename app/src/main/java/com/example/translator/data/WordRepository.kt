package com.example.translator.data

import android.content.Context
import kotlinx.coroutines.flow.Flow

interface WordRepository {
    val getAllWords: Flow<List<Word>>
    fun getWord(id: Int): Flow<Word>
    suspend fun insert(word: Word)
    suspend fun update(word: Word)
    suspend fun delete(word: Word)
    suspend fun  getAllWordsRepo(): Flow<List<Word>>

}
class WordRepositoryImpl(context: Context) : WordRepository {
    private val wordDao: WordDao = WordDatabase.Companion.getDatabase(context).wordDao()
    override fun getWord(id: Int): Flow<Word> = wordDao.getWord(id)
    override suspend fun insert(word: Word) = wordDao.insert(word)
    override suspend fun update(word: Word) = wordDao.update(word)
    override suspend fun delete(word: Word) = wordDao.delete(word)
    override val getAllWords: Flow<List<Word>> =wordDao.getAllWords()
    override suspend fun getAllWordsRepo(): Flow<List<Word>> =wordDao.getAllWords()


}