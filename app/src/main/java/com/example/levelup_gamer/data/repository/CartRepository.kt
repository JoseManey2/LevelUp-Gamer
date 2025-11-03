package com.example.levelup_gamer.data.repository

import com.example.levelup_gamer.data.models.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class CartItem(val product: Product, val quantity: Int)

object CartRepository {
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    fun addToCart(product: Product) {
        val currentItems = _cartItems.value.toMutableList()
        val existingItem = currentItems.find { it.product.id == product.id }

        if (existingItem != null) {
            val updatedItem = existingItem.copy(quantity = existingItem.quantity + 1)
            val itemIndex = currentItems.indexOf(existingItem)
            currentItems[itemIndex] = updatedItem
        } else {
            currentItems.add(CartItem(product = product, quantity = 1))
        }
        _cartItems.value = currentItems
    }

    fun removeFromCart(productId: String) {
        _cartItems.value = _cartItems.value.filterNot { it.product.id == productId }
    }

    fun updateQuantity(productId: String, newQuantity: Int) {
        if (newQuantity <= 0) {
            removeFromCart(productId)
        } else {
            val currentItems = _cartItems.value.toMutableList()
            val existingItem = currentItems.find { it.product.id == productId }
            if (existingItem != null) {
                val updatedItem = existingItem.copy(quantity = newQuantity)
                val itemIndex = currentItems.indexOf(existingItem)
                currentItems[itemIndex] = updatedItem
                _cartItems.value = currentItems
            }
        }
    }
}