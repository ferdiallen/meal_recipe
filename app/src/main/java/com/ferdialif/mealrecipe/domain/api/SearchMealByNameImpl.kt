package com.ferdialif.mealrecipe.domain.api

import com.ferdialif.mealrecipe.BuildConfig
import com.ferdialif.mealrecipe.domain.model.MealDetailList
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class SearchMealByNameImpl @Inject constructor(
    private val client: HttpClient
) : SearchMealByName {
    override suspend fun searchMealByName(name: String): MealDetailList {
        return try {
            client.get("${BuildConfig.BASE_URL}/search.php") {
                parameter("s", name)
            }.body()
        } catch (e: Exception) {
            MealDetailList()
        }
    }
}