package com.ferdialif.mealrecipe.domain.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ferdialif.mealrecipe.domain.model.MealCategoryRecipeDetail
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMealDao {
    @Query("SELECT * FROM favorite_db")
    fun getListOfFavoriteMeal(): Flow<List<MealCategoryRecipeDetail>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewFavorite(data: MealCategoryRecipeDetail)

    @Delete
    suspend fun deleteFavoriteMenu(data: MealCategoryRecipeDetail)
}