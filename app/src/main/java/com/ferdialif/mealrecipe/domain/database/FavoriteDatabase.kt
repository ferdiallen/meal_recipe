package com.ferdialif.mealrecipe.domain.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ferdialif.mealrecipe.domain.dao.FavoriteMealDao
import com.ferdialif.mealrecipe.domain.model.MealCategoryRecipeDetail

@Database(entities = [MealCategoryRecipeDetail::class], version = 1, exportSchema = false)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun dao(): FavoriteMealDao
}