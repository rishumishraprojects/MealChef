package com.example.mealchef.ui.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.mealchef.data.Meal
import com.example.mealchef.data.roomDB.CategoryEntity

@Composable
fun CategoryScreen(
    onCategoryClick: (String) -> Unit,
    onMealClick: (String) -> Unit, // <--- 1. NEW: Need this to click search results
    viewModel: CategoryViewModel = hiltViewModel()
) {
    val categories = viewModel.categories.collectAsState()
    val searchQuery = viewModel.searchQuery.collectAsState()
    val searchResults = viewModel.searchResults.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        // --- 2. SEARCH BAR ---
        OutlinedTextField(
            value = searchQuery.value,
            onValueChange = { viewModel.onSearchQueryChange(it) },
            label = { Text("Search recipes...") },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --- 3. SWITCH: SEARCH vs CATEGORIES ---
        if (searchQuery.value.isNotEmpty()) {
            // A) SHOW SEARCH RESULTS
            if (searchResults.value.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No recipes found.")
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(searchResults.value) { meal ->
                        SearchMealItem(meal, onMealClick)
                    }
                }
            }
        } else {
            // B) SHOW CATEGORIES (Your existing code)
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories.value) { category ->
                    CategoryItem(category = category, onClick = {
                        onCategoryClick(category.strCategory)
                    })
                }
            }
        }
    }
}

// Your existing CategoryItem
@Composable
fun CategoryItem(category: CategoryEntity, onClick: () -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.fillMaxWidth().height(100.dp).clickable { onClick() }
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            AsyncImage(
                model = category.strCategoryThumb,
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = category.strCategory, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = category.strCategoryDescription,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 3
                )
            }
        }
    }
}

// NEW: Item for Search Results
@Composable
fun SearchMealItem(meal: Meal, onClick: (String) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().height(80.dp).clickable { onClick(meal.id) },
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
            AsyncImage(
                model = meal.thumbnail,
                contentDescription = null,
                modifier = Modifier.size(60.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = meal.name, style = MaterialTheme.typography.titleMedium)
        }
    }
}