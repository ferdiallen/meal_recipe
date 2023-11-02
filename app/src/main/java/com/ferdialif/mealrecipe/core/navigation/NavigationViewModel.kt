package com.ferdialif.mealrecipe.core.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ferdialif.mealrecipe.domain.model.MealCategoryRecipeDetail
import com.ferdialif.mealrecipe.domain.repository.MealRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(private val repository: MealRepository):ViewModel() {
    fun insertToFavorite(data: MealCategoryRecipeDetail) = viewModelScope.launch(Dispatchers.IO) {
        repository.addBookmark(data)
    }
    fun removeFromFavorite(data:MealCategoryRecipeDetail) = viewModelScope.launch (Dispatchers.IO){
        repository.removeBookmark(data)
    }
}