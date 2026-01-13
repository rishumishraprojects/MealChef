package com.example.mealchef

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mealchef.ui.theme.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                // We moved the UI into a separate function "MainScreen" to keep this clean
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    // 1. Watch the navigation to see which tab should be highlighted
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            NavigationBar {
                // --- HOME TAB ---
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    // Highlight if we are on Category List OR Meal List OR Details
                    selected = currentRoute == Screen.CategoryList.route,
                    onClick = {
                        navController.navigate(Screen.CategoryList.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )

                // --- FAVORITES TAB ---
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Favorite, contentDescription = "Favorites") },
                    label = { Text("Favorites") },
                    selected = currentRoute == Screen.Favourites.route,
                    onClick = {
                        navController.navigate(Screen.Favourites.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        // 2. The Container for screens
        NavHost(
            navController = navController,
            startDestination = Screen.CategoryList.route,
            modifier = Modifier.padding(innerPadding) // <--- Important: Respect Bottom Bar height
        ) {
            // HOME SCREEN
            composable(Screen.CategoryList.route) {
                CategoryScreen(
                    onCategoryClick = { categoryName ->
                        navController.navigate(Screen.MealList.createRoute(categoryName))
                    },
                    onMealClick = { mealId ->
                        navController.navigate(Screen.RecipeDetail.createRoute(mealId))
                    }
                )
            }

            // MEAL LIST SCREEN
            composable(
                route = Screen.MealList.route,
                arguments = listOf(
                    navArgument("category_id") { type = NavType.StringType }
                )
            ) {
                MealsScreen(
                    onMealClick = { mealId ->
                        navController.navigate(Screen.RecipeDetail.createRoute(mealId))
                    }
                )
            }

            // RECIPE DETAIL SCREEN
            composable(
                route = Screen.RecipeDetail.route,
                arguments = listOf(
                    navArgument("recipe_id") { type = NavType.StringType }
                )
            ) {
                RecipeDetailScreen()
            }

            // FAVORITES SCREEN (New!)
            composable(Screen.Favourites.route) {
                FavoritesScreen(
                    onMealClick = { mealId ->
                        navController.navigate(Screen.RecipeDetail.createRoute(mealId))
                    }
                )
            }
        }
    }
}