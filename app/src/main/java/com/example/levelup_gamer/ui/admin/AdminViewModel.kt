package com.example.levelup_gamer.ui.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup_gamer.data.models.Product
import com.example.levelup_gamer.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class AdminUiState(
    val products: List<Product> = emptyList(),
    val productToDelete: Product? = null
)

class AdminViewModel(private val productRepository: ProductRepository) : ViewModel() {
    private val _productToDelete = MutableStateFlow<Product?>(null)

    val uiState: StateFlow<AdminUiState> = combine(
        productRepository.products,
        _productToDelete
    ) { products, productToDelete ->
        AdminUiState(products, productToDelete)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = AdminUiState()
    )

    fun onDeleteProductClicked(product: Product) {
        _productToDelete.value = product
    }

    fun onConfirmDelete() {
        _productToDelete.value?.let { product ->
            viewModelScope.launch {
                productRepository.deleteProduct(product.id)
                _productToDelete.value = null
            }
        }
    }

    fun onDismissDelete() {
        _productToDelete.value = null
    }
}
