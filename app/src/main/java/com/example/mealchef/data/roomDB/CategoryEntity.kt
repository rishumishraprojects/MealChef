package com.example.mealchef.data.roomDB

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey
    val idCategory: String,

    val strCategory: String,

    val strCategoryThumb: String,

    val strCategoryDescription: String
)