package com.example.levelup_gamer.ui.product_detail

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
// IMPORTS DE COIL NECESARIOS
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest
import com.example.levelup_gamer.R
import com.example.levelup_gamer.ui.AppViewModelProvider
import com.example.levelup_gamer.ui.home.BottomNavigationBar
import com.example.levelup_gamer.ui.theme.Black
import com.example.levelup_gamer.ui.theme.ElectricBlue
import com.example.levelup_gamer.ui.theme.NeonGreen
import com.example.levelup_gamer.ui.theme.White
import com.example.levelup_gamer.ui.theme.orbitron
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    navController: NavController,
    productDetailViewModel: ProductDetailViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by productDetailViewModel.uiState.collectAsState()
    val product = uiState.product

    if (uiState.showAddedToCartPopup) {
        AlertDialog(
            onDismissRequest = { productDetailViewModel.dismissPopup() },
            title = { Text("¡Éxito!") },
            text = { Text("Producto añadido existosamente") },
            confirmButton = {
                TextButton(onClick = { productDetailViewModel.dismissPopup() }) {
                    Text("Aceptar")
                }
            }
        )
    }

    Scaffold(
        containerColor = Black,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = product?.name ?: "",
                        fontFamily = orbitron,
                        color = NeonGreen,
                        maxLines = 2,
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(painterResource(id = R.drawable.ic_back_arrow), contentDescription = "Back", tint = NeonGreen)
                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(painterResource(id = R.drawable.ic_search), contentDescription = "Search", tint = NeonGreen)
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(painterResource(id = R.drawable.ic_share), contentDescription = "Share", tint = NeonGreen)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Black
                )
            )
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
            if (product == null) {
                CircularProgressIndicator(color = NeonGreen)
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    val listState = rememberLazyListState()
                    var currentPage by remember { mutableStateOf(0) }

                    if (product.imageUrls.isNotEmpty()) {
                        LaunchedEffect(listState) {
                            snapshotFlow { listState.firstVisibleItemIndex }
                                .distinctUntilChanged()
                                .map { index -> currentPage = index }
                                .collect {}
                        }

                        Box(contentAlignment = Alignment.TopCenter) {
                            LazyRow(
                                state = listState,
                                contentPadding = PaddingValues(horizontal = 16.dp)
                            ) {
                                itemsIndexed(product.imageUrls) { _, imageUrl ->

                                    // -----------------------------------------------------------
                                    // AQUI ESTA LA CONFIGURACION CORRECTA DE COIL PARA DEBUGGING
                                    // -----------------------------------------------------------
                                    AsyncImage(
                                        model = ImageRequest.Builder(LocalContext.current)
                                            // URL DE PRUEBA (ZELDA)
                                            .data("https://upload.wikimedia.org/wikipedia/en/c/c6/The_Legend_of_Zelda_Breath_of_the_Wild.jpg")
                                            .crossfade(true)
                                            .build(),
                                        contentDescription = product.name,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .height(300.dp)
                                            .padding(horizontal = 4.dp)
                                            .clip(RoundedCornerShape(16.dp))
                                            .background(Color.Gray),

                                        // Estos funcionan perfectamente con onError
                                        placeholder = painterResource(R.drawable.ic_launcher_foreground),
                                        error = painterResource(R.drawable.ic_launcher_foreground),

                                        // CAMBIO IMPORTANTE: Usamos onError en vez de onState
                                        onError = { state ->
                                            val error = state.result.throwable
                                            Log.e("COIL_ERROR", "--------------------------------------")
                                            Log.e("COIL_ERROR", "🚨 FALLO AL CARGAR IMAGEN")
                                            Log.e("COIL_ERROR", "🔍 MENSAJE: ${error.message}")
                                            Log.e("COIL_ERROR", "🔍 CAUSA: ${error.cause}")
                                            Log.e("COIL_ERROR", "--------------------------------------")
                                            error.printStackTrace()
                                        }
                                    )
                                    // -----------------------------------------------------------
                                }
                            }
                            Text(
                                text = "${currentPage + 1}/${product.imageUrls.size}",
                                color = White,
                                modifier = Modifier
                                    .background(Color.Black.copy(alpha = 0.5f))
                                    .padding(8.dp)
                                    .clip(RoundedCornerShape(8.dp))
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = product.name,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = White,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (product.discountedPrice != null) {
                            Text(
                                text = "${product.price}",
                                color = Color.Gray,
                                textDecoration = TextDecoration.LineThrough,
                                fontSize = 20.sp
                            )
                            Spacer(modifier = Modifier.padding(horizontal = 8.dp))
                            Text(
                                text = "${product.discountedPrice}",
                                color = NeonGreen,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                        } else {
                            Text(
                                text = "${product.price}",
                                color = NeonGreen,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        if (product.stock > 0) {
                            Text("Producto en Stock", color = NeonGreen)
                        } else {
                            Text("Agotado", color = Color.Red)
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Cantidad:", color = White, fontSize = 18.sp)
                        IconButton(onClick = { productDetailViewModel.onQuantityChanged(uiState.quantity - 1) }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Remove one", tint = White)
                        }
                        Text(uiState.quantity.toString(), color = White, fontSize = 18.sp)
                        IconButton(onClick = { productDetailViewModel.onQuantityChanged(uiState.quantity + 1) }) {
                            Icon(Icons.Filled.Add, contentDescription = "Add one", tint = White)
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = product.description,
                        color = White,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { productDetailViewModel.addToCart() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = NeonGreen),
                        enabled = product.stock > 0
                    ) {
                        Text("AÑADIR AL CARRITO", color = Black, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = ElectricBlue)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Detalles del Producto", fontWeight = FontWeight.Bold, color = White)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Categoría: ${product.category}", color = White)
                            Text("Tipo: --", color = White)
                            Text("Formato Disponible: --", color = White)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = ElectricBlue)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Reseñas del Producto", fontWeight = FontWeight.Bold, color = White)
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Filled.Star, contentDescription = "Star", tint = Color.Yellow)
                                Icon(Icons.Filled.Star, contentDescription = "Star", tint = Color.Yellow)
                                Icon(Icons.Filled.Star, contentDescription = "Star", tint = Color.Yellow)
                                Icon(Icons.Filled.Star, contentDescription = "Star", tint = Color.Yellow)
                                Icon(Icons.Filled.Star, contentDescription = "Star", tint = Color.Gray)
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Nombre de la persona", color = White)
                            Text("Lorem ipsum dolor sit amet, consectetur adipiscing elit. sed", color = White)
                        }
                    }
                }
            }
        }
    }
}