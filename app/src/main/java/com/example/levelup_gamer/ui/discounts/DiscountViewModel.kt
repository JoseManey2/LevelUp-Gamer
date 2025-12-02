package com.example.levelup_gamer.ui.discounts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup_gamer.data.models.Product
import com.example.levelup_gamer.data.repository.ProductRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class DiscountUiState(
    val discountedProducts: List<Product> = emptyList()
)

class DiscountViewModel(productRepository: ProductRepository) : ViewModel() {
    val uiState: StateFlow<DiscountUiState> = 
        productRepository.products
            .map { allProducts ->
                DiscountUiState(discountedProducts = allProducts.filter { it.discountedPrice != null })
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = DiscountUiState()
            )
}
