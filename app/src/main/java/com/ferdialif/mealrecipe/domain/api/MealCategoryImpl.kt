package com.ferdialif.mealrecipe.domain.api

import com.ferdialif.mealrecipe.BuildConfig
import com.ferdialif.mealrecipe.domain.model.MealCategories
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class MealCategoryImpl (
    private val client: HttpClient
) : GetMealCategory {
    override suspend fun getMealCategory(): MealCategories {
        return client.get("${BuildConfig.BASE_URL}/categories.php") {

        }.body()
    }
}