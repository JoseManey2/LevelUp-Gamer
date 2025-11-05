package com.example.levelup_gamer.data.models

data class Product(
    val id: String = "",
    val category: String = "",
    val name: String = "",
    val price: String = "",
    val imageUrl: Int = 0,
    val description: String = "",
    val discountedPrice: String? = null
)
