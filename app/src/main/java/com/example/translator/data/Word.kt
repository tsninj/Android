package com.example.translator.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words")

data class Word(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val english: String,
    val mongolia: String,
)
