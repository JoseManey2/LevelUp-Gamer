package com.example.levelup_gamer

import android.app.Application
import android.content.Context
import com.example.levelup_gamer.data.database.AppDatabase
import com.example.levelup_gamer.data.initialProducts
import com.example.levelup_gamer.data.network.ApiService
import com.example.levelup_gamer.data.repository.ProductRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val productRepository: ProductRepository
}

class DefaultAppContainer(private val context: Context) : AppContainer {
    private val baseUrl = "https://raw.githubusercontent.com/"

    private val json = Json { ignoreUnknownKeys = true }

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    override val productRepository: ProductRepository by lazy {
        ProductRepository(AppDatabase.getDatabase(context).productDao(), apiService)
    }

    init {
        // Pre-populate the database only if it's empty.
        // This makes the initial data loading more robust.
        CoroutineScope(Dispatchers.IO).launch {
            if (productRepository.products.first().isEmpty()) {
                productRepository.insertProducts(initialProducts)
            }
        }
    }
}

class LevelUpGamerApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}