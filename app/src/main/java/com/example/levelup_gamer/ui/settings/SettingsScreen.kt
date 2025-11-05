package com.example.levelup_gamer.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.levelup_gamer.R
import com.example.levelup_gamer.ui.home.BottomNavigationBar
import com.example.levelup_gamer.ui.navigation.AppDrawer
import com.example.levelup_gamer.ui.theme.Black
import com.example.levelup_gamer.ui.theme.NeonGreen
import com.example.levelup_gamer.ui.theme.White
import com.example.levelup_gamer.ui.theme.orbitron
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    settingsViewModel: SettingsViewModel = viewModel()
) {
    val uiState by settingsViewModel.uiState.collectAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    if (uiState.showLogoutConfirmDialog) {
        AlertDialog(
            onDismissRequest = { settingsViewModel.onDismissLogoutDialog() },
            title = { Text("Cerrar Sesión") },
            text = { Text("¿Estás seguro de que quieres cerrar sesión?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        settingsViewModel.onDismissLogoutDialog()
                        navController.navigate("welcome") {
                            popUpTo(navController.graph.startDestinationId) { inclusive = true }
                        }
                    }
                ) {
                    Text("Sí, estoy seguro")
                }
            },
            dismissButton = {
                TextButton(onClick = { settingsViewModel.onDismissLogoutDialog() }) {
                    Text("No, volver a la app")
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
                    title = { Text("CONFIGURACIÓN", fontFamily = orbitron, color = NeonGreen) },
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                SettingItem(title = "Notificaciones") {
                    Switch(
                        checked = uiState.notificationsEnabled,
                        onCheckedChange = { settingsViewModel.onNotificationsToggled(it) }
                    )
                }
                SettingItem(title = "Modo Oscuro") {
                    Switch(
                        checked = uiState.darkModeEnabled,
                        onCheckedChange = { settingsViewModel.onDarkModeToggled(it) }
                    )
                }
                LanguageSettingItem(selectedLanguage = uiState.selectedLanguage, onLanguageSelected = settingsViewModel::onLanguageSelected)
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = { settingsViewModel.onLogoutClicked() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = NeonGreen)
                ) {
                    Text("CERRAR SESIÓN", color = Black)
                }
            }
        }
    }
}

@Composable
fun SettingItem(title: String, content: @Composable () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, color = White)
        content()
    }
}

@Composable
fun LanguageSettingItem(selectedLanguage: String, onLanguageSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val languages = listOf("Español", "English")

    SettingItem(title = "Idioma") {
        Box {
            Text(text = selectedLanguage, color = White, modifier = Modifier.clickable { expanded = true })
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                languages.forEach { language ->
                    DropdownMenuItem(text = { Text(language) }, onClick = { 
                        onLanguageSelected(language)
                        expanded = false 
                    })
                }
            }
        }
    }
}