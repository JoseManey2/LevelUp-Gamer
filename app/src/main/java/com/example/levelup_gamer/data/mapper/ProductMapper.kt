package com.example.levelup_gamer.data.mapper

import com.example.levelup_gamer.data.models.Product
import com.example.levelup_gamer.data.network.NetworkProduct

fun NetworkProduct.toDomainProduct(): Product {
    return Product(
        id = this.id,
        name = this.name,
        category = this.category,
        price = this.price.toString(), // Convert Long to String
        discountedPrice = this.discountedPrice?.toString(), // Convert Long? to String?
        description = this.description,
        logoImageUrl = this.logoImageUrl,
        imageUrls = this.imageUrls,
        // If API does not provide stock, assume it is available.
        stock = if (this.stock <= 0) 100 else this.stock,
        stockPorSucursal = emptyMap() // As requested, this field does not come from the API
    )
}

fun List<NetworkProduct>.toDomainProducts(): List<Product> {
    return this.map { it.toDomainProduct() }
}
