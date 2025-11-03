package com.example.levelup_gamer.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.levelup_gamer.ui.UserProfile.UserProfileScreen
import com.example.levelup_gamer.ui.admin.AdminScreen
import com.example.levelup_gamer.ui.cart.ShoppingCartScreen
import com.example.levelup_gamer.ui.discounts.DiscountScreen
import com.example.levelup_gamer.ui.home.HomeScreen
import com.example.levelup_gamer.ui.login.LoginScreen
import com.example.levelup_gamer.ui.product_detail.ProductDetailScreen
import com.example.levelup_gamer.ui.product_detail.ProductDetailViewModel
import com.example.levelup_gamer.ui.register.RegisterScreen
import com.example.levelup_gamer.ui.settings.SettingsScreen
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
            RegisterScreen(navController = navController)
        }
        composable("home") {
            HomeScreen(navController = navController)
        }
        composable(
            route = "productDetail/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) {
            ProductDetailScreen(navController = navController, productDetailViewModel = viewModel())
        }
        composable("admin") {
            AdminScreen(navController = navController)
        }
        composable("discounts") {
            DiscountScreen(navController = navController)
        }
        composable("profile") {
            UserProfileScreen(navController = navController)
        }
        composable("cart") {
            ShoppingCartScreen(navController = navController)
        }
        composable("settings") {
            SettingsScreen(navController = navController)
        }
    }
}
