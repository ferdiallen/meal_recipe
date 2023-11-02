package com.ferdialif.mealrecipe.domain.api

import com.ferdialif.mealrecipe.domain.model.MealDetailList

interface SearchMealByName {
    suspend fun searchMealByName(name: String): MealDetailList
}