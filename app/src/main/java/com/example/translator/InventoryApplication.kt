package com.example.translator

import android.app.Application
import com.example.translator.data.AppContainer
import com.example.translator.data.AppDataContainer

class InventoryApplication : Application() {


    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}