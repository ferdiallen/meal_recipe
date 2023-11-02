package com.ferdialif.mealrecipe.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MealCategories(
    @SerialName("categories")
    val category: List<MealCategory>
)

@Serializable
data class MealCategory(
    val idCategory: String? = "",
    val strCategory: String? = "",
    val strCategoryDescription: String? = "",
    val strCategoryThumb: String? = ""
)