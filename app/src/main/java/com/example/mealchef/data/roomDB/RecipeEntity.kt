package com.example.mealchef.data.roomDB

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey val id : String,
    val name : String,
    val thumbnail : String,
)
