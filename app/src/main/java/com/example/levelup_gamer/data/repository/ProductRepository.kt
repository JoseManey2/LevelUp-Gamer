package com.example.levelup_gamer.data.repository

import com.example.levelup_gamer.data.models.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object ProductRepository {
    private val initialProducts = listOf(
        Product(id = "JM001", category = "Juegos de Mesa", name = "Catan", price = "29.990", description = "Un clásico juego de estrategia donde los jugadores compiten por colonizar y expandirse en la isla de Catan. Ideal para 3-4 jugadores y perfecto para noches de juego en familia o con amigos.", discountedPrice = "23.990"),
        Product(id = "JM002", category = "Juegos de Mesa", name = "Carcassonne", price = "24.990", description = "Un juego de colocación de fichas donde los jugadores construyen el paisaje alrededor de la fortaleza medieval de Carcassonne. Ideal para 2-5 jugadores y fácil de aprender."),
        Product(id = "AC001", category = "Accesorios", name = "Controlador Inalámbrico Xbox Series X", price = "59.990", description = "Ofrece una experiencia de juego cómoda con botones mapeables y una respuesta táctil mejorada. Compatible con consolas Xbox y PC."),
        Product(id = "AC002", category = "Accesorios", name = "Auriculares Gamer HyperX Cloud II", price = "79.990", description = "Proporcionan un sonido envolvente de calidad con un micrófono desmontable y almohadillas de espuma viscoelástica para mayor comodidad durante largas sesiones de juego.", discountedPrice = "63.990"),
        Product(id = "CO001", category = "Consolas", name = "PlayStation 5", price = "549.990", description = "La consola de última generación de Sony, que ofrece gráficos impresionantes y tiempos de carga ultrarrápidos para una experiencia de juego inmersiva."),
        Product(id = "CG001", category = "Computadores Gamers", name = "PC Gamer ASUS ROG Strix", price = "1.299.990", description = "Un potente equipo diseñado para los gamers más exigentes, equipado con los últimos componentes para ofrecer un rendimiento excepcional en cualquier juego."),
        Product(id = "SG001", category = "Sillas Gamers", name = "Silla Gamer Secretlab Titan", price = "349.990", description = "Diseñada para el máximo confort, esta silla ofrece un soporte ergonómico y personalización ajustable para sesiones de juego prolongadas."),
        Product(id = "MS001", category = "Mouse", name = "Mouse Gamer Logitech G502 HERO", price = "49.990", description = "Con sensor de alta precisión y botones personalizables, este mouse es ideal para gamers que buscan un control preciso y personalización.", discountedPrice = "39.990"),
        Product(id = "MP001", category = "Mousepad", name = "Mousepad Razer Goliathus Extended Chroma", price = "29.990", description = "Ofrece un área de juego amplia con iluminación RGB personalizable, asegurando una superficie suave y uniforme para el movimiento del mouse."),
        Product(id = "PP001", category = "Poleras Personalizadas", name = "Polera Gamer Personalizada 'Level-Up'", price = "14.990", description = "Una camiseta cómoda y estilizada, con la posibilidad de personalizarla con tu gamer tag o diseño favorito.")
    )

    private val _products = MutableStateFlow(initialProducts)
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    fun getProductById(id: String): Product? {
        return _products.value.find { it.id == id }
    }

    fun deleteProduct(productId: String) {
        _products.value = _products.value.filterNot { it.id == productId }
    }

    fun addProduct(product: Product) {
        _products.value = _products.value + product
    }
    
    fun updateProduct(product: Product) {
        _products.value = _products.value.map { if(it.id == product.id) product else it }
    }
}
