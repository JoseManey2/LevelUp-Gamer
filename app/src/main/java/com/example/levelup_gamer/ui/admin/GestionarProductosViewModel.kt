package com.example.levelup_gamer.ui.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup_gamer.data.initialProducts
import com.example.levelup_gamer.data.models.Product
import com.example.levelup_gamer.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class GestionarProductosUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val productsFromApi: List<Product>? = null,
    val showSaveConfirmation: Boolean = false,
    val showResetConfirmation: Boolean = false,
    val selectedProducts: Map<String, Boolean> = emptyMap()
)

class GestionarProductosViewModel(private val productRepository: ProductRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(GestionarProductosUiState())
    val uiState: StateFlow<GestionarProductosUiState> = _uiState.asStateFlow()

    fun onProductSelectionChanged(productId: String, isSelected: Boolean) {
        _uiState.update {
            it.copy(selectedProducts = it.selectedProducts.toMutableMap().also {
                it[productId] = isSelected
            })
        }
    }

    fun onSelectAllChanged(isSelected: Boolean) {
        _uiState.value.productsFromApi?.let { products ->
            _uiState.update {
                it.copy(selectedProducts = products.associate { product -> product.id to isSelected })
            }
        }
    }

    fun loadProductsFromApi() {
        viewModelScope.launch {
            _uiState.value = GestionarProductosUiState(isLoading = true)
            try {
                val products = productRepository.refreshProductsFromApi()
                _uiState.value = GestionarProductosUiState(
                    isLoading = false,
                    productsFromApi = products,
                    selectedProducts = products.associate { it.id to true } // Select all by default
                )
            } catch (e: Exception) {
                _uiState.value = GestionarProductosUiState(isLoading = false, error = e.message)
            }
        }
    }

    fun saveSelectedProductsToLocalDb() {
        _uiState.value.productsFromApi?.let { products ->
            viewModelScope.launch {
                val selectedIds = _uiState.value.selectedProducts.filter { it.value }.keys
                val productsToSave = products.filter { it.id in selectedIds }
                productRepository.insertProducts(productsToSave)
                _uiState.value = _uiState.value.copy(showSaveConfirmation = true)
            }
        }
    }

    fun onResetDatabaseClicked() {
        _uiState.update { it.copy(showResetConfirmation = true) }
    }

    fun onResetDatabaseConfirmed() {
        viewModelScope.launch {
            productRepository.deleteAllProducts()
            productRepository.insertProducts(initialProducts)
            _uiState.value = GestionarProductosUiState() // Reset the screen state
        }
    }

    fun onResetDatabaseDismissed() {
        _uiState.update { it.copy(showResetConfirmation = false) }
    }

    fun dismissAll() {
        _uiState.value = GestionarProductosUiState()
    }
}
