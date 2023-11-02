package com.ferdialif.mealrecipe.domain.api

import com.ferdialif.mealrecipe.domain.model.MealDetailList

interface GetMealsDetails {
    suspend fun getMealDetails(mealId: String): MealDetailList
}