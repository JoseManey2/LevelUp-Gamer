package com.example.levelup_gamer.data.repository

import com.example.levelup_gamer.data.database.dao.ProductDao
import com.example.levelup_gamer.data.mapper.toDomainProducts
import com.example.levelup_gamer.data.models.Product
import com.example.levelup_gamer.data.network.ApiService
import kotlinx.coroutines.flow.Flow

class ProductRepository(
    private val productDao: ProductDao,
    private val apiService: ApiService
) {

    val products: Flow<List<Product>> = productDao.getProducts()

    // Return a nullable product to handle cases where the product is not found
    fun getProductById(id: String): Flow<Product?> {
        return productDao.getProductById(id)
    }

    suspend fun refreshProductsFromApi(): List<Product> {
        val networkProducts = apiService.getProducts()
        return networkProducts.toDomainProducts()
    }

    suspend fun insertProducts(products: List<Product>) {
        productDao.insertProducts(products)
    }

    suspend fun deleteAllProducts() {
        productDao.deleteAllProducts()
    }

    suspend fun deleteProduct(productId: String) {
        productDao.deleteProduct(productId)
    }
}
