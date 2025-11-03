package com.example.levelup_gamer.ui.product_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.levelup_gamer.R
import com.example.levelup_gamer.ui.home.BottomNavigationBar
import com.example.levelup_gamer.ui.theme.Black
import com.example.levelup_gamer.ui.theme.ElectricBlue
import com.example.levelup_gamer.ui.theme.NeonGreen
import com.example.levelup_gamer.ui.theme.orbitron

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    navController: NavController,
    productDetailViewModel: ProductDetailViewModel = viewModel()
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
                    if (product != null) {
                        Text(text = product.name, fontFamily = orbitron, color = NeonGreen)
                    }
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
        if (product != null) {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(16.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(ElectricBlue)
                ) {
                    Text("IMAGEN/ES DEL PRODUCTO", color = Color.White, modifier = Modifier.align(Alignment.Center))
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = product.name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.padding(horizontal = 16.dp)) {
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
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = product.description,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { productDetailViewModel.addToCart() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = NeonGreen)
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
                        Text("Detalles del Producto", fontWeight = FontWeight.Bold, color = Color.White)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Categoría: ${product.category}", color = Color.White)
                        Text("Tipo: --", color = Color.White)
                        Text("Formato Disponible: --", color = Color.White)
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
                        Text("Reseñas del Producto", fontWeight = FontWeight.Bold, color = Color.White)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Star, contentDescription = "Star", tint = Color.Yellow)
                            Icon(Icons.Default.Star, contentDescription = "Star", tint = Color.Yellow)
                            Icon(Icons.Default.Star, contentDescription = "Star", tint = Color.Yellow)
                            Icon(Icons.Default.Star, contentDescription = "Star", tint = Color.Yellow)
                            Icon(Icons.Default.Star, contentDescription = "Star", tint = Color.Gray)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Nombre de la persona", color = Color.White)
                        Text("Lorem ipsum dolor sit amet, consectetur adipiscing elit. sed", color = Color.White)
                    }
                }
            }
        }
    }
}
