package com.ferdialif.mealrecipe.domain.api

import com.ferdialif.mealrecipe.domain.model.MealCategoryRecipe

interface GetMealByCategory {
    suspend fun getMealByCategory(category: String): MealCategoryRecipe
}