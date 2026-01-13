package com.example.mealchef.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mealchef.data.Category
import com.example.mealchef.data.Meal
import com.example.mealchef.data.MealRepository
import com.example.mealchef.data.roomDB.CategoryEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CategoryViewModel @Inject constructor(private val repository : MealRepository) : ViewModel() {

    private val  _categories = MutableStateFlow<List<CategoryEntity>>(emptyList())
    val categories : StateFlow<List<CategoryEntity>> = _categories

    // 2. NEW: Search Logic
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _searchResults = MutableStateFlow<List<Meal>>(emptyList())
    val searchResults: StateFlow<List<Meal>> = _searchResults

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            // 1. OBSERVE DATABASE (Fast path)
            repository.getCategoriesFromDb().collect { cachedList ->
                if (cachedList.isNotEmpty()) {
                    _categories.value = cachedList
                }
            }
        }

        viewModelScope.launch {
            // 2. FETCH FROM NETWORK (Slow path - Background)
            // This will update the DB, which triggers the collect above automatically
            repository.refreshCategories()
        }
    }

    // 3. Function to handle typing
    fun onSearchQueryChange(newQuery: String) {
        _searchQuery.value = newQuery

        // If text is empty, clear results. If not, fetch data.
        if (newQuery.isBlank()) {
            _searchResults.value = emptyList()
        } else {
            viewModelScope.launch {
                try {
                    _searchResults.value = repository.searchMeals(newQuery)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

}