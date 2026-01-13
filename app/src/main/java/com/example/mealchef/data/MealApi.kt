package com.example.mealchef.data

import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("categories.php")
    suspend fun getCategories() : CategoryResponse

    @GET("filter.php")
    suspend fun getMealsByCategory(@Query("c") categoryName : String) : MealResponse

    @GET("lookup.php")
    suspend fun getRecipeDetail(@Query("i") id : String) : RecipeResponse

    @GET("search.php")
    suspend fun searchMeals(@Query("s") query: String): MealResponse

}