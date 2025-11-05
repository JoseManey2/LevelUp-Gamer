package com.example.levelup_gamer.ui.product_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup_gamer.data.models.Product
import com.example.levelup_gamer.data.repository.CartRepository
import com.example.levelup_gamer.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class ProductDetailUiState(
    val product: Product? = null,
    val showAddedToCartPopup: Boolean = false,
    val quantity: Int = 1
)

class ProductDetailViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    private val productId: String = checkNotNull(savedStateHandle["productId"])
    private val _showAddedToCartPopup = MutableStateFlow(false)
    private val _quantity = MutableStateFlow(1)

    val uiState: StateFlow<ProductDetailUiState> = 
        combine(ProductRepository.products, _showAddedToCartPopup, _quantity) { products, showPopup, quantity ->
            ProductDetailUiState(
                product = products.find { it.id == productId },
                showAddedToCartPopup = showPopup,
                quantity = quantity
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ProductDetailUiState()
        )

    fun onQuantityChanged(newQuantity: Int) {
        if (newQuantity > 0) {
            _quantity.value = newQuantity
        }
    }

    fun addToCart() {
        uiState.value.product?.let {
            CartRepository.addToCart(it, _quantity.value)
            _showAddedToCartPopup.value = true
        }
    }

    fun dismissPopup() {
        _showAddedToCartPopup.value = false
    }
}