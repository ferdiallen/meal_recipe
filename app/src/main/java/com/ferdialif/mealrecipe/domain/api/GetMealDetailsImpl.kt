package com.ferdialif.mealrecipe.domain.api

import com.ferdialif.mealrecipe.BuildConfig
import com.ferdialif.mealrecipe.domain.model.MealDetailList
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class GetMealDetailsImpl @Inject constructor(
    private val client: HttpClient
) : GetMealsDetails {
    override suspend fun getMealDetails(mealId: String): MealDetailList {
        return try {
            client.get("${BuildConfig.BASE_URL}/lookup.php") {
                parameter("i", mealId)
            }.body<MealDetailList>()
        } catch (e: Exception) {
            println(e.localizedMessage)
            MealDetailList(emptyList())
        }
    }
}