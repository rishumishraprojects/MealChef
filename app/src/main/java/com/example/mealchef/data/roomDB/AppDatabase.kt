package com.example.mealchef.data.roomDB

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [RecipeEntity::class,CategoryEntity::class] , version = 2)
abstract class AppDatabase : RoomDatabase(){
    abstract fun recipeDao(): RecipeDao
    abstract fun categoryDao(): CategoryDao
}