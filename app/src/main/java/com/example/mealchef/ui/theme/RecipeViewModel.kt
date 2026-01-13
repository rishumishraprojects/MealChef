package com.example.mealchef.ui.theme

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealchef.data.MealRepository
import com.example.mealchef.data.RecipeDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val repository: MealRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _recipe = MutableStateFlow<RecipeDetail?>(null)
    val recipe: StateFlow<RecipeDetail?> = _recipe

    private val _isSaved = MutableStateFlow(false)
    val isSaved: StateFlow<Boolean> = _isSaved

    init {
        val recipeId = savedStateHandle.get<String>("recipe_id") ?: ""
        if (recipeId.isNotEmpty()) {
            fetchRecipe(recipeId)
        }
    }

    private fun fetchRecipe(id: String) {
        viewModelScope.launch {
            try {
                val detail = repository.getRecipeDetail(id)
                _recipe.value = detail
                _isSaved.value = repository.isRecipeSaved(id)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            val currentRecipe = _recipe.value

            if (currentRecipe == null) {
                Log.e("CHEF_DEBUG", "Error: Recipe is NULL. Cannot save.")
                return@launch
            }

            if (_isSaved.value) {
                // It was saved, so we delete it
                Log.d("CHEF_DEBUG", "Deleting recipe: ${currentRecipe.name}")
                repository.deleteRecipe(currentRecipe)
                _isSaved.value = false
            } else {
                // It was not saved, so we save it
                Log.d("CHEF_DEBUG", "Saving recipe: ${currentRecipe.name}")
                repository.saveRecipe(currentRecipe)
                _isSaved.value = true
            }
        }
    }
}