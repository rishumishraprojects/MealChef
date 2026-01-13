package com.example.mealchef.data

import com.google.gson.annotations.SerializedName

data class RecipeResponse(
    @SerializedName("meals")
    val meals: List<RecipeDetail>
)

data class RecipeDetail(
    @SerializedName("idMeal")
    val id: String,

    @SerializedName("strMeal")
    val name: String,

    @SerializedName("strMealThumb")
    val thumbnail: String,

    @SerializedName("strInstructions")
    val instructions: String,

    @SerializedName("strYoutube")
    val youtubeUrl: String
)