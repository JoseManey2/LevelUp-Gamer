package com.example.levelup_gamer.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AppDrawer(
    navController: NavController,
    drawerState: DrawerState,
    scope: CoroutineScope
) {
    ModalDrawerSheet {
        Text("Level-Up Gamer", modifier = Modifier.padding(16.dp))
        NavigationDrawerItem(
            label = { Text("Inicio") },
            selected = false,
            onClick = { 
                navController.navigate("home")
                scope.launch { drawerState.close() }
            },
            icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") }
        )
        NavigationDrawerItem(
            label = { Text("Ofertas") },
            selected = false,
            onClick = { 
                navController.navigate("discounts")
                scope.launch { drawerState.close() }
            },
            icon = { Text("20%") }
        )
        NavigationDrawerItem(
            label = { Text("Perfil") },
            selected = false,
            onClick = { 
                navController.navigate("profile")
                scope.launch { drawerState.close() }
            },
            icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") }
        )
        NavigationDrawerItem(
            label = { Text("Carrito") },
            selected = false,
            onClick = { 
                navController.navigate("cart")
                scope.launch { drawerState.close() }
            },
            icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito") }
        )
        NavigationDrawerItem(
            label = { Text("Configuración") },
            selected = false,
            onClick = { 
                navController.navigate("settings")
                scope.launch { drawerState.close() }
            },
            icon = { Icon(Icons.Default.Settings, contentDescription = "Configuración") }
        )
    }
}