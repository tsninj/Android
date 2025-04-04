
package com.example.translator.data

import android.content.Context

interface AppContainer {
    val wordRepository: WordRepository
}


class AppDataContainer(private val context: Context) : AppContainer {

    override val wordRepository: WordRepository by lazy {
        WordRepositoryImpl(WordDatabase.getDatabase(context).wordDao())
    }
}
