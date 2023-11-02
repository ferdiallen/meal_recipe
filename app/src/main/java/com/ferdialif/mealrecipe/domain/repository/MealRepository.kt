package com.ferdialif.mealrecipe.domain.repository

import com.ferdialif.mealrecipe.domain.api.GetMealByCategory
import com.ferdialif.mealrecipe.domain.api.GetMealCategory
import com.ferdialif.mealrecipe.domain.api.GetMealsDetails
import com.ferdialif.mealrecipe.domain.api.SearchMealByName
import com.ferdialif.mealrecipe.domain.dao.FavoriteMealDao
import com.ferdialif.mealrecipe.domain.database.FavoriteDatabase
import com.ferdialif.mealrecipe.domain.model.MealCategoryRecipeDetail

import javax.inject.Inject

class MealRepository @Inject constructor(
    private val category: GetMealCategory,
    private val recipeByCategory: GetMealByCategory,
    private val mealDetails: GetMealsDetails,
    private val searchMeal: SearchMealByName,
    private val dao: FavoriteDatabase
) {
    suspend fun getMealCategories() = category.getMealCategory()
    suspend fun getMealCategoryById(category: String) = recipeByCategory.getMealByCategory(category)
    suspend fun getMealDetails(mealId: String) = mealDetails.getMealDetails(mealId)
    suspend fun searchMealByName(name: String) = searchMeal.searchMealByName(name)
    fun listBookmark() = dao.dao().getListOfFavoriteMeal()
    suspend fun addBookmark(data: MealCategoryRecipeDetail) = dao.dao().insertNewFavorite(data)
    suspend fun removeBookmark(data: MealCategoryRecipeDetail) = dao.dao().deleteFavoriteMenu(data)
}