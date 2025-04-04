package com.example.translator.data

import kotlinx.coroutines.flow.Flow

interface WordRepository {
    suspend fun getAllWords(): Flow<List<Word>>
    fun getWord(id: Int): Flow<Word>
    suspend fun insert(word: Word)
    suspend fun update(word: Word)
    suspend fun delete(word: Word)
}
class WordRepositoryImpl(private val wordDao: WordDao) : WordRepository {
    override fun getWord(id: Int): Flow<Word> = wordDao.getWord(id)
    override suspend fun insert(word: Word) = wordDao.insert(word)
    override suspend fun update(word: Word) = wordDao.update(word)
    override suspend fun delete(word: Word) = wordDao.delete(word)
    override suspend fun getAllWords(): Flow<List<Word>> =wordDao.getAllWords()

}
