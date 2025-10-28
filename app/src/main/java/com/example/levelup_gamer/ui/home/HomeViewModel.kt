package com.example.levelup_gamer.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup_gamer.data.models.Product
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val database = Firebase.database
    private val productsRef = database.getReference("products")

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    init {
        addProductsToDatabase() // Adds or updates products with image URLs
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
                // Handle error
            }
        })
    }

    private fun addProductsToDatabase() {
        val productList = listOf(
            Product("JM001", "Juegos de Mesa", "Catan", "$29.990 CLP", "https://cf.geekdo-images.com/W3_elpoQWH_buAcWgTuMAA__itemrep/img/SoOL_o0-Y54TFa3i1vC_9L0d92I=/fit-in/246x300/filters:strip_icc()/pic2419375.jpg"),
            Product("JM002", "Juegos de Mesa", "Carcassonne", "$24.990 CLP", "https://cf.geekdo-images.com/okM0dq_bEXnbyQTOvHfwRA__itemrep/img/eX_2yV3b1R_pQy9T1iK3b37b1t0=/fit-in/246x300/filters:strip_icc()/pic6544250.jpg"),
            Product("AC001", "Accesorios", "Controlador Inalámbrico Xbox Series X", "$59.990 CLP", "https://http2.mlstatic.com/D_NQ_NP_927393-MLA49924903739_052022-O.webp"),
            Product("AC002", "Accesorios", "Auriculares Gamer HyperX Cloud II", "$79.990 CLP", "https://media.solotodo.com/media/products/129759_picture_1653327179.webp"),
            Product("CO001", "Consolas", "PlayStation 5", "$549.990 CLP", "https://http2.mlstatic.com/D_NQ_NP_841787-MLA44484414455_012021-O.webp"),
            Product("CG001", "Computadores Gamers", "PC Gamer ASUS ROG Strix", "$1.299.990 CLP", "https://www.paris.cl/dw/image/v2/BCHB_PRD/on/demandware.static/-/Sites-cencosud-master-catalog/default/dw3b3a6b57/images/hi-res/201042499-00001.jpg"),
            Product("SG001", "Sillas Gamers", "Silla Gamer Secretlab Titan", "$349.990 CLP", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTOahfE7aLT_32PA_b_h8i3v1A9_CHO-2Yl9A&s"),
            Product("MS001", "Mouse", "Mouse Gamer Logitech G502 HERO", "$49.990 CLP", "https://media.solotodo.com/media/products/37459_picture_1583597036.webp"),
            Product("MP001", "Mousepad", "Mousepad Razer Goliathus Extended Chroma", "$29.990 CLP", "https://http2.mlstatic.com/D_NQ_NP_821376-MLA48483864115_122021-O.webp"),
            Product("PP001", "Poleras Personalizadas", "Polera Gamer Personalizada 'Level-Up'", "$14.990 CLP", "https://http2.mlstatic.com/D_NQ_NP_908581-MLC49925621815_052022-O.webp")
        )

        productList.forEach { product ->
            productsRef.child(product.id).setValue(product)
        }
    }
}
