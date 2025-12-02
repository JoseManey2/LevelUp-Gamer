package com.example.levelup_gamer.data.network

import retrofit2.http.GET

interface ApiService {
    // This should expect the API's model, not the app's model
    @GET("chalalo1533/ServicioRest/refs/heads/master/productos.json")
    suspend fun getProducts(): List<NetworkProduct>
}
