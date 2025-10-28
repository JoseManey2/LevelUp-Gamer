package com.example.levelup_gamer.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.levelup_gamer.ui.home.HomeScreen
import com.example.levelup_gamer.ui.login.LoginScreen
import com.example.levelup_gamer.ui.welcome.WelcomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "welcome") {
        composable("welcome") {
            WelcomeScreen(navController = navController)
        }
        composable("login") {
            LoginScreen(navController = navController)
        }
        composable("register") {
            // Redirigiendo a LoginScreen, que maneja ambos modos.
            // Puedes cambiar esto a una pantalla de registro dedicada si lo prefieres.
            LoginScreen(navController = navController)
        }
        composable("home") {
            HomeScreen()
        }
    }
}
