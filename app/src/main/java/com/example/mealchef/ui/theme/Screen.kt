package com.example.mealchef.ui.theme

sealed class Screen(val route : String) {

    object CategoryList : Screen("category_list")
    object Favourites : Screen("favourites")

    object MealList : Screen("meal_list/{category_id}"){
        fun createRoute(categoryId : String) = "meal_list/$categoryId"
    }

    object RecipeDetail : Screen("recipe_detail/{recipe_id}"){
        fun createRoute(recipeId : String) = "recipe_detail/$recipeId"
    }



}