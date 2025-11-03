package com.example.levelup_gamer.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup_gamer.data.models.Product
import com.example.levelup_gamer.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class HomeUiState(
    val products: List<Product> = emptyList(),
    val categories: List<String> = emptyList(),
    val searchQuery: String = "",
    val selectedCategory: String? = null
)

class HomeViewModel : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    private val _selectedCategory = MutableStateFlow<String?>(null)

    val uiState: StateFlow<HomeUiState> = combine(
        ProductRepository.products,
        _searchQuery,
        _selectedCategory
    ) { allProducts, query, category ->
        val productsToShow = if (category != null) {
            allProducts.filter { it.category == category }
        } else {
            allProducts
        }
        val searchedProducts = if (query.isNotBlank()) {
            productsToShow.filter { it.name.contains(query, ignoreCase = true) }
        } else {
            productsToShow
        }
        HomeUiState(
            products = searchedProducts,
            categories = allProducts.map { it.category }.distinct(),
            searchQuery = query,
            selectedCategory = category
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeUiState()
    )

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun onCategorySelected(category: String) {
        if (_selectedCategory.value == category) {
            _selectedCategory.value = null
        } else {
            _selectedCategory.value = category
        }
    }
}
