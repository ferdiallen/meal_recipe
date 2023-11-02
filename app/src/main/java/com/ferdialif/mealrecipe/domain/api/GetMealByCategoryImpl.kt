package com.ferdialif.mealrecipe.domain.api

import com.ferdialif.mealrecipe.BuildConfig
import com.ferdialif.mealrecipe.domain.model.MealCategoryRecipe
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class GetMealByCategoryImpl @Inject constructor(
    private val httpClient: HttpClient
) : GetMealByCategory {
    override suspend fun getMealByCategory(category: String): MealCategoryRecipe {
        return httpClient.get("${BuildConfig.BASE_URL}/filter.php") {
            parameter("c", category)
        }.body()
    }
}