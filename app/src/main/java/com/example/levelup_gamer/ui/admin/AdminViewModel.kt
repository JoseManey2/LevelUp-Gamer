package com.example.levelup_gamer.ui.admin

import androidx.lifecycle.ViewModel
import com.example.levelup_gamer.data.models.Product
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AdminViewModel : ViewModel() {

    private val database = Firebase.database
    private val productsRef = database.getReference("products")

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        productsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val productList = snapshot.children.mapNotNull {
                    it.getValue(Product::class.java)
                }
                _products.value = productList
            }

            override fun onCancelled(error: DatabaseError) {
                // TODO: Handle error properly, maybe expose an error state.
            }
        })
    }

    fun addProduct(product: Product) {
        // Generates a new unique key for the product
        val newProductRef = productsRef.push()
        newProductRef.setValue(product.copy(id = newProductRef.key!!))
    }

    fun updateProduct(product: Product) {
        productsRef.child(product.id).setValue(product)
    }

    fun deleteProduct(productId: String) {
        productsRef.child(productId).removeValue()
    }
}
