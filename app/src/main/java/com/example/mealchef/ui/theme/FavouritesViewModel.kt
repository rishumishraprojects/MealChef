package com.example.mealchef.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealchef.data.MealRepository
import com.example.mealchef.data.roomDB.RecipeEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: MealRepository
) : ViewModel() {

    private val _favoriteMeals = MutableStateFlow<List<RecipeEntity>>(emptyList())
    val favoriteMeals: StateFlow<List<RecipeEntity>> = _favoriteMeals

    init {
        getFavorites()
    }

    private fun getFavorites() {
        viewModelScope.launch {
            // We collect the Flow from the DB so it updates automatically!
            repository.getAllRecipes().collect {
                _favoriteMeals.value = it
            }
        }
    }


}