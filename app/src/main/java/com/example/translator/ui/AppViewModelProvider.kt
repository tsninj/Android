//
//package com.example.translator.ui
//
//import android.app.Application
//import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
//import androidx.lifecycle.createSavedStateHandle
//import androidx.lifecycle.viewmodel.CreationExtras
//import androidx.lifecycle.viewmodel.initializer
//import androidx.lifecycle.viewmodel.viewModelFactory
//import com.example.translator.InventoryApplication
//import com.example.translator.ui.home.HomeViewModel
//import com.example.translator.ui.change.ChangeViewModel
//
//object AppViewModelProvider {
//    val Factory = viewModelFactory {
//
//
//        initializer {
//            HomeViewModel(inventoryApplication().container.itemsRepository)
//        }
//    }
//}
//
///**
// * Extension function to queries for [Application] object and returns an instance of
// * [InventoryApplication].
// */
//fun CreationExtras.inventoryApplication(): InventoryApplication =
//    (this[AndroidViewModelFactory.APPLICATION_KEY] as InventoryApplication)