package com.ferdialif.mealrecipe.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
data class MealCategoryRecipe(
    val meals: List<MealCategoryRecipeDetail>
)

@Serializable
@Entity("favorite_db")
data class MealCategoryRecipeDetail(
    val strMeal: String = "",
    val strMealThumb: String = "",
    @PrimaryKey
    val idMeal: String = "",
)