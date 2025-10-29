package com.example.levelup_gamer.ui.admin

import androidx.lifecycle.ViewModel
import com.example.levelup_gamer.data.models.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AdminViewModel : ViewModel() {
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products = _products.asStateFlow()

    init {
        // Cargar productos de ejemplo
        _products.value = listOf(
            Product(id = "1", name = "Teclado Gamer", category = "Periféricos", price = "75.99"),
            Product(id = "2", name = "Mouse Inalámbrico", category = "Periféricos", price = "45.50"),
            Product(id = "3", name = "Monitor 24 pulgadas", category = "Monitores", price = "250.00")
        )
    }

    fun deleteProduct(productId: String) {
        _products.value = _products.value.filterNot { it.id == productId }
    }
}
