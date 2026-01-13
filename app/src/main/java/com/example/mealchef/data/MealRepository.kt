package com.example.mealchef.data

import com.example.mealchef.data.roomDB.CategoryDao
import com.example.mealchef.data.roomDB.CategoryEntity
import com.example.mealchef.data.roomDB.RecipeDao
import com.example.mealchef.data.roomDB.RecipeEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MealRepository @Inject constructor(private val mealApi: MealApi,private val dao : RecipeDao,private val mealDao : CategoryDao) {

    // 1. The UI only observes this Database Flow (Instant Load)
    fun getCategoriesFromDb(): Flow<List<CategoryEntity>> {
        return mealDao.getAllCategories()
    }

    suspend fun getCategories(): List<Category> {
        return mealApi.getCategories().categories
    }

    suspend fun getMealsByCategory(category : String) : List<Meal>{
        return mealApi.getMealsByCategory(category).meals
    }

    // --- Detail & DB Logic ---

    suspend fun getRecipeDetail(id: String): RecipeDetail {
        return mealApi.getRecipeDetail(id).meals.first()
    }

    suspend fun isRecipeSaved(id: String): Boolean {
        return dao.isRecipeSaved(id)
    }

    suspend fun saveRecipe(recipe: RecipeDetail) {
        val entity = RecipeEntity(recipe.id, recipe.name, recipe.thumbnail)
        dao.insertRecipe(entity)
    }

    suspend fun deleteRecipe(recipe: RecipeDetail) {
        val entity = RecipeEntity(recipe.id, recipe.name, recipe.thumbnail)
        dao.deleteRecipe(entity)
    }

    suspend fun getAllRecipes() = dao.getAllRecipes()

    // 2. This runs in the background to update the Database
    suspend fun refreshCategories() {
        try {
            // Fetch from API
            val response = mealApi.getCategories()

            // Map API response to Database Entity
            val categoryEntities = response.categories.map {
                CategoryEntity(it.id, it.name, it.thumbnail, it.description)
            }

            // Save to DB (The Flow above will auto-update the UI!)
            mealDao.insertCategories(categoryEntities)
        } catch (e: Exception) {
            // If offline, we do nothing. The user still sees the old DB data!
            e.printStackTrace()
        }
    }

    suspend fun searchMeals(query: String): List<Meal> {
        val response = mealApi.searchMeals(query)
        // The API returns null if nothing is found, so we return an empty list instead
        return response.meals ?: emptyList()
    }
}



