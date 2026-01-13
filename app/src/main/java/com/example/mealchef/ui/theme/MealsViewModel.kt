package com.example.mealchef.ui.theme

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealchef.data.Meal
import com.example.mealchef.data.MealRepository
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MealsViewModel @Inject constructor(private val repository : MealRepository,savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _meals = MutableStateFlow<List<Meal>>(emptyList())
    val meals : StateFlow<List<Meal>> = _meals

    init{
        val category = savedStateHandle.get<String>("category_id") ?: ""
        if (category.isNotEmpty()) {
            fetchMeals(category)
        }
    }

     fun fetchMeals(category : String) {
        viewModelScope.launch{
            try{
                _meals.value = repository.getMealsByCategory(category)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}