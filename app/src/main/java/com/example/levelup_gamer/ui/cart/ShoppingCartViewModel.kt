package com.example.levelup_gamer.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup_gamer.data.repository.CartItem
import com.example.levelup_gamer.data.repository.CartRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class ShoppingCartUiState(
    val cartItems: List<CartItem> = emptyList(),
    val total: Double = 0.0
)

class ShoppingCartViewModel : ViewModel() {
    val uiState: StateFlow<ShoppingCartUiState> = 
        CartRepository.cartItems
            .map { items ->
                val total = items.sumOf { 
                    val priceString = it.product.discountedPrice ?: it.product.price
                    priceString.replace(".", "").replace(',', '.').toDouble() * it.quantity
                }
                ShoppingCartUiState(
                    cartItems = items,
                    total = total
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = ShoppingCartUiState()
            )

    fun onQuantityChanged(productId: String, quantity: Int) {
        CartRepository.updateQuantity(productId, quantity)
    }

    fun onRemoveItem(productId: String) {
        CartRepository.removeFromCart(productId)
    }
}