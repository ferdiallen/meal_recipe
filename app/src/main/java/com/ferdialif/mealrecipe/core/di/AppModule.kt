package com.ferdialif.mealrecipe.core.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ferdialif.mealrecipe.domain.api.GetMealByCategory
import com.ferdialif.mealrecipe.domain.api.GetMealByCategoryImpl
import com.ferdialif.mealrecipe.domain.api.GetMealCategory
import com.ferdialif.mealrecipe.domain.api.GetMealDetailsImpl
import com.ferdialif.mealrecipe.domain.api.GetMealsDetails
import com.ferdialif.mealrecipe.domain.api.MealCategoryImpl
import com.ferdialif.mealrecipe.domain.api.SearchMealByName
import com.ferdialif.mealrecipe.domain.api.SearchMealByNameImpl
import com.ferdialif.mealrecipe.domain.dao.FavoriteMealDao
import com.ferdialif.mealrecipe.domain.database.FavoriteDatabase
import com.ferdialif.mealrecipe.domain.model.MealCategoryRecipeDetail
import com.ferdialif.mealrecipe.domain.repository.MealRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesRoomDatabase(@ApplicationContext context: Context): FavoriteDatabase {
        return Room.databaseBuilder(context, FavoriteDatabase::class.java, "favorite_db").build()
    }

    @Provides
    @Singleton
    fun providesClientApi(): HttpClient {
        return HttpClient(Android) {
            install(ContentNegotiation) {
                json()
            }
        }
    }

    @Provides
    @Singleton
    fun providesMealCategoryApi(client: HttpClient): GetMealCategory {
        return MealCategoryImpl(client)
    }

    @Provides
    @Singleton
    fun providesMealRepository(
        category: GetMealCategory,
        recipeByCategory: GetMealByCategory,
        details: GetMealsDetails,
        search: SearchMealByName,
        favorite: FavoriteDatabase
    ): MealRepository {
        return MealRepository(category, recipeByCategory, details, search, favorite)
    }

    @Provides
    @Singleton
    fun providesMealRecipeByCategory(client: HttpClient): GetMealByCategory {
        return GetMealByCategoryImpl(client)
    }

    @Provides
    @Singleton
    fun providesMealDetailsRecipe(client: HttpClient): GetMealsDetails {
        return GetMealDetailsImpl(client)
    }

    @Provides
    @Singleton
    fun providesSearchMealByName(client: HttpClient): SearchMealByName {
        return SearchMealByNameImpl(client)
    }

}