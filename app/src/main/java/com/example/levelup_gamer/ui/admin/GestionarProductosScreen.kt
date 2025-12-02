package com.example.levelup_gamer.ui.admin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.levelup_gamer.ui.AppViewModelProvider
import com.example.levelup_gamer.ui.theme.Black
import com.example.levelup_gamer.ui.theme.NeonGreen
import com.example.levelup_gamer.ui.theme.White
import com.example.levelup_gamer.ui.theme.orbitron

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GestionarProductosScreen(
    navController: NavController,
    viewModel: GestionarProductosViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.error != null) {
        AlertDialog(
            onDismissRequest = { viewModel.dismissAll() },
            title = { Text("Error") },
            text = { Text("Error al cargar desde la API: ${uiState.error}") },
            confirmButton = {
                TextButton(onClick = { viewModel.dismissAll() }) {
                    Text("Aceptar")
                }
            }
        )
    }

    if (uiState.showSaveConfirmation) {
        AlertDialog(
            onDismissRequest = { viewModel.dismissAll() },
            title = { Text("Éxito") },
            text = { Text("Los productos seleccionados se han guardado en la base de datos local.") },
            confirmButton = {
                TextButton(onClick = {
                    navController.navigate("home") {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }) {
                    Text("Genial")
                }
            }
        )
    }

    if (uiState.showResetConfirmation) {
        AlertDialog(
            onDismissRequest = { viewModel.onResetDatabaseDismissed() },
            title = { Text("Confirmar Acción") },
            text = { Text("¿Estás seguro de que quieres borrar TODOS los productos y restaurar los datos de fábrica?") },
            confirmButton = {
                TextButton(onClick = { viewModel.onResetDatabaseConfirmed() }) {
                    Text("Sí, restaurar")
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.onResetDatabaseDismissed() }) {
                    Text("Cancelar")
                }
            }
        )
    }

    Scaffold(
        containerColor = Black,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("GESTIONAR PRODUCTOS", fontFamily = orbitron, color = NeonGreen) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = NeonGreen)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Black
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(color = NeonGreen)
            } else if (uiState.productsFromApi != null) {
                val allSelected = uiState.selectedProducts.values.all { it }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                    ) {
                        Checkbox(
                            checked = allSelected,
                            onCheckedChange = { viewModel.onSelectAllChanged(!allSelected) }
                        )
                        Text("Seleccionar Todos", color = White)
                    }
                    LazyColumn(modifier = Modifier.weight(1f)) {
                        items(uiState.productsFromApi!!) { product ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                            ) {
                                Checkbox(
                                    checked = uiState.selectedProducts[product.id] ?: false,
                                    onCheckedChange = { isSelected ->
                                        viewModel.onProductSelectionChanged(product.id, isSelected)
                                    }
                                )
                                Text(product.name, color = White)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { viewModel.saveSelectedProductsToLocalDb() }) {
                        Text(text = "Guardar Productos Seleccionados")
                    }
                }
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Button(onClick = { viewModel.loadProductsFromApi() }) {
                        Text(text = "Cargar productos desde la API")
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(onClick = { viewModel.onResetDatabaseClicked() }) {
                        Text(text = "Restaurar BD a Datos de Fábrica")
                    }
                }
            }
        }
    }
}