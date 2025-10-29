package com.example.levelup_gamer.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.levelup_gamer.R
import com.example.levelup_gamer.data.models.Product
import com.example.levelup_gamer.ui.theme.Black
import com.example.levelup_gamer.ui.theme.ElectricBlue
import com.example.levelup_gamer.ui.theme.NeonGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, homeViewModel: HomeViewModel = viewModel()) {
    val uiState by homeViewModel.uiState.collectAsState()

    Scaffold(
        containerColor = Black,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    TextField(
                        value = uiState.searchQuery,
                        onValueChange = { homeViewModel.onSearchQueryChange(it) },
                        placeholder = { Text("Buscar productos") },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                        shape = RoundedCornerShape(25.dp),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        modifier = Modifier.height(50.dp)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(painterResource(id = R.drawable.ic_menu), contentDescription = "Menu", tint = NeonGreen)
                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(painterResource(id = R.drawable.ic_notifications), contentDescription = "Notifications", tint = NeonGreen)
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(painterResource(id = R.drawable.ic_bookmarks), contentDescription = "Bookmarks", tint = NeonGreen)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Black
                )
            )
        },
        bottomBar = {
            BottomNavigationBar()
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            // TODO: Category filters
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(uiState.products) { product ->
                    ProductItem(product = product, onClick = { navController.navigate("productDetail/${product.id}") })
                }
            }
        }
    }
}

@Composable
fun ProductItem(product: Product, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        modifier = Modifier
            .background(Color.Transparent)
            .clickable(onClick = onClick)

    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(ElectricBlue) // Placeholder for image
            ) {
                Text("IMAGEN DEL PRODUCTO", color = Color.White, modifier = Modifier.align(Alignment.Center))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .background(NeonGreen)
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = product.name,
                    color = Black,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar() {
    NavigationBar(containerColor = Black) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home", tint = NeonGreen) },
            selected = true,
            onClick = { /*TODO*/ }
        )
        NavigationBarItem(
            icon = { Text("20%", color = NeonGreen, fontWeight = FontWeight.Bold, fontSize = 12.sp) },
            selected = false,
            onClick = { /*TODO*/ }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile", tint = NeonGreen) },
            selected = false,
            onClick = { /*TODO*/ }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Cart", tint = NeonGreen) },
            selected = false,
            onClick = { /*TODO*/ }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Settings, contentDescription = "Settings", tint = NeonGreen) },
            selected = false,
            onClick = { /*TODO*/ }
        )
    }
}
