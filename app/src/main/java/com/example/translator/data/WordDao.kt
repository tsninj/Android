package com.example.translator.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Word)

    @Update
    suspend fun update(word: Word)

    @Delete
    suspend fun delete(word: Word)

    @Query("SELECT * from words WHERE id = :id")
    fun getWord(id: Int): Flow<Word>

    @Query("SELECT * from words ORDER BY id ASC")
    fun getAllWords(): Flow<List<Word>>
}
