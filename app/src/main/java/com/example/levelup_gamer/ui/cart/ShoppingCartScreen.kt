package com.example.levelup_gamer.ui.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.levelup_gamer.R
import com.example.levelup_gamer.data.repository.CartItem
import com.example.levelup_gamer.ui.home.BottomNavigationBar
import com.example.levelup_gamer.ui.navigation.AppDrawer
import com.example.levelup_gamer.ui.theme.Black
import com.example.levelup_gamer.ui.theme.ElectricBlue
import com.example.levelup_gamer.ui.theme.NeonGreen
import com.example.levelup_gamer.ui.theme.White
import com.example.levelup_gamer.ui.theme.orbitron
import java.text.NumberFormat
import java.util.Locale
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingCartScreen(
    navController: NavController,
    shoppingCartViewModel: ShoppingCartViewModel = viewModel()
) {
    val uiState by shoppingCartViewModel.uiState.collectAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    uiState.productToDelete?.let { item ->
        AlertDialog(
            onDismissRequest = { shoppingCartViewModel.onDismissRemoveDialog() },
            title = { Text("Eliminar Producto") },
            text = { Text("¿Estás seguro de que quieres borrar el producto \"${item.product.name}\"?") },
            confirmButton = {
                TextButton(onClick = { shoppingCartViewModel.onConfirmRemoveItem() }) {
                    Text("Sí, estoy seguro")
                }
            },
            dismissButton = {
                TextButton(onClick = { shoppingCartViewModel.onDismissRemoveDialog() }) {
                    Text("No, no quiero borrarlo")
                }
            }
        )
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawer(navController = navController, drawerState = drawerState, scope = scope)
        }
    ) {
        Scaffold(
            containerColor = Black,
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("CARRITO DE COMPRAS", fontFamily = orbitron, color = NeonGreen) },
                    navigationIcon = {
                        IconButton(onClick = { 
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(painterResource(id = R.drawable.ic_menu), contentDescription = "Menu", tint = NeonGreen)
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Black)
                )
            },
            bottomBar = { BottomNavigationBar(navController = navController) }
        ) { paddingValues ->
            if (uiState.cartItems.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Tu carrito está vacío", color = White, fontSize = 20.sp)
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    LazyColumn(modifier = Modifier.weight(1f)) {
                        items(uiState.cartItems) { item ->
                            CartItemRow(item = item, onQuantityChanged = shoppingCartViewModel::onQuantityChanged, onRemoveItem = { shoppingCartViewModel.onRemoveItemClicked(item) })
                        }
                    }
                    Column(modifier = Modifier.padding(16.dp)) {
                        val formatter = NumberFormat.getCurrencyInstance(Locale.Builder().setLanguage("es").setRegion("CL").build())
                        Text(
                            text = "Total: ${formatter.format(uiState.total)}",
                            color = White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.End)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { /* TODO: Navigate to checkout */ },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = NeonGreen)
                        ) {
                            Text("PAGAR", color = Black, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CartItemRow(
    item: CartItem,
    onQuantityChanged: (String, Int) -> Unit,
    onRemoveItem: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(ElectricBlue)
        ) // Placeholder for image
        Spacer(modifier = Modifier.padding(horizontal = 8.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = item.product.name, color = White, fontWeight = FontWeight.Bold)
            val price = item.product.discountedPrice ?: item.product.price
            Text(text = price, color = NeonGreen)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { onQuantityChanged(item.product.id, item.quantity - 1) }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Remove one", tint = White)
            }
            Text(text = item.quantity.toString(), color = White, fontWeight = FontWeight.Bold)
            IconButton(onClick = { onQuantityChanged(item.product.id, item.quantity + 1) }) {
                Icon(Icons.Default.Add, contentDescription = "Add one", tint = White)
            }
            IconButton(onClick = onRemoveItem) {
                Icon(Icons.Default.Delete, contentDescription = "Remove item", tint = Color.Red)
            }
        }
    }
}