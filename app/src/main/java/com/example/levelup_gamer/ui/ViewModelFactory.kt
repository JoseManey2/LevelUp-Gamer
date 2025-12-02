package com.example.levelup_gamer.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.levelup_gamer.LevelUpGamerApplication
import com.example.levelup_gamer.ui.admin.AdminViewModel
import com.example.levelup_gamer.ui.admin.GestionarProductosViewModel
import com.example.levelup_gamer.ui.discounts.DiscountViewModel
import com.example.levelup_gamer.ui.home.HomeViewModel
import com.example.levelup_gamer.ui.product_detail.ProductDetailViewModel

object AppViewModelProvider {
    val Factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
            val application = dependencies(extras)
            val savedStateHandle = extras.createSavedStateHandle()

            return when {
                modelClass.isAssignableFrom(AdminViewModel::class.java) -> {
                    AdminViewModel(application.container.productRepository) as T
                }
                modelClass.isAssignableFrom(ProductDetailViewModel::class.java) -> {
                    ProductDetailViewModel(application.container.productRepository, savedStateHandle) as T
                }
                modelClass.isAssignableFrom(DiscountViewModel::class.java) -> {
                    DiscountViewModel(application.container.productRepository) as T
                }
                modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                    HomeViewModel(application.container.productRepository) as T
                }
                modelClass.isAssignableFrom(GestionarProductosViewModel::class.java) -> {
                    GestionarProductosViewModel(application.container.productRepository) as T
                }
                else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
            }
        }
    }
}

fun dependencies(extras: CreationExtras): LevelUpGamerApplication = 
    (extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as LevelUpGamerApplication)
