package com.example.levelup_gamer.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup_gamer.data.repository.CartItem
import com.example.levelup_gamer.data.repository.CartRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class ShoppingCartUiState(
    val cartItems: List<CartItem> = emptyList(),
    val total: Double = 0.0,
    val productToDelete: CartItem? = null // New state to hold the item to be deleted
)

class ShoppingCartViewModel : ViewModel() {
    private val _productToDelete = MutableStateFlow<CartItem?>(null)

    val uiState: StateFlow<ShoppingCartUiState> = 
        combine(CartRepository.cartItems, _productToDelete) { items, productToDelete ->
            val total = items.sumOf { 
                val priceString = it.product.discountedPrice ?: it.product.price
                priceString.replace(".", "").replace(',', '.').toDouble() * it.quantity
            }
            ShoppingCartUiState(
                cartItems = items,
                total = total,
                productToDelete = productToDelete
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

    fun onRemoveItemClicked(item: CartItem) {
        _productToDelete.value = item
    }

    fun onConfirmRemoveItem() {
        _productToDelete.value?.let {
            CartRepository.removeFromCart(it.product.id)
        }
        _productToDelete.value = null
    }

    fun onDismissRemoveDialog() {
        _productToDelete.value = null
    }
}