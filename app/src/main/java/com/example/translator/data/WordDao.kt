package com.example.translator.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
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

