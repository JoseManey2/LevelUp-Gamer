package com.example.levelup_gamer.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Divider
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
            icon = { Icon(Icons.Filled.Home, contentDescription = "Inicio") }
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
            icon = { Icon(Icons.Filled.Person, contentDescription = "Perfil") }
        )
        NavigationDrawerItem(
            label = { Text("Carrito") },
            selected = false,
            onClick = { 
                navController.navigate("cart")
                scope.launch { drawerState.close() }
            },
            icon = { Icon(Icons.Filled.ShoppingCart, contentDescription = "Carrito") }
        )
        NavigationDrawerItem(
            label = { Text("Configuración") },
            selected = false,
            onClick = { 
                navController.navigate("settings")
                scope.launch { drawerState.close() }
            },
            icon = { Icon(Icons.Filled.Settings, contentDescription = "Configuración") }
        )
        Divider(modifier = Modifier.padding(vertical = 16.dp))
        Text("Admin", modifier = Modifier.padding(16.dp))
        NavigationDrawerItem(
            label = { Text("Ver Productos") },
            selected = false,
            onClick = { 
                navController.navigate("admin")
                scope.launch { drawerState.close() }
            },
            icon = { Icon(Icons.Filled.AdminPanelSettings, contentDescription = "Admin") }
        )
        NavigationDrawerItem(
            label = { Text("Gestionar Productos") },
            selected = false,
            onClick = { 
                navController.navigate("gestionar_productos")
                scope.launch { drawerState.close() }
            },
            icon = { Icon(Icons.Filled.CloudDownload, contentDescription = "Gestionar Productos") }
        )
    }
}