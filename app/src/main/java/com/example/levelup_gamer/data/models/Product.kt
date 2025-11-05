package com.example.levelup_gamer.data.models

data class Product(
    val id: String = "",
    val category: String = "",
    val name: String = "",
    val price: String = "",
    val logoImageUrl: String = "",
    val imageUrls: List<String> = emptyList(),
    val description: String = "",
    val discountedPrice: String? = null,
    val stock: Int = 0
)
