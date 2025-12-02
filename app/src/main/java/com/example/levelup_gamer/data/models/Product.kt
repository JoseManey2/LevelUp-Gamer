package com.example.levelup_gamer.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

// This is the main model used by the app (Domain and Database)
@Entity(tableName = "products")
data class Product(
    @PrimaryKey val id: String = "",
    val category: String = "",
    val name: String = "",
    val price: String = "", // Price is a String as expected by the UI
    val logoImageUrl: String = "",
    val imageUrls: List<String> = emptyList(),
    val description: String = "",
    val discountedPrice: String? = null, // Discount price is also a String
    val stock: Int = 0,
    val stockPorSucursal: Map<String, Int> = emptyMap() // This field does not come from the API
)
