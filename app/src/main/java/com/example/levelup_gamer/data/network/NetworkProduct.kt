package com.example.levelup_gamer.data.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// This class represents the structure of the product object from the API
@Serializable
data class NetworkProduct(
    @SerialName("sku")
    val id: String,

    @SerialName("nombre")
    val name: String,

    @SerialName("categoria")
    val category: String,

    @SerialName("precio")
    val price: Long, // The API sends price as a number

    @SerialName("precio_descuento")
    val discountedPrice: Long? = null, // Discount price is also a number

    @SerialName("descripcion")
    val description: String,

    @SerialName("logo_url")
    val logoImageUrl: String = "",

    @SerialName("imagenes_url")
    val imageUrls: List<String> = emptyList(),

    // Stock is optional in the API. Default to 0 if missing.
    val stock: Int = 0
)
