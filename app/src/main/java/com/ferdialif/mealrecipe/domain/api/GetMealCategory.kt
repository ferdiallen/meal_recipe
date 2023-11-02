package com.ferdialif.mealrecipe.domain.api

import com.ferdialif.mealrecipe.domain.model.MealCategories

interface GetMealCategory {
    suspend fun getMealCategory():MealCategories
}