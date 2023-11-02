package com.ferdialif.mealrecipe.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ferdialif.mealrecipe.domain.model.MealCategory
import com.ferdialif.mealrecipe.domain.model.MealCategoryRecipeDetail
import com.ferdialif.mealrecipe.domain.model.MealDetails
import com.ferdialif.mealrecipe.domain.repository.MealRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MealRepository
) : ViewModel() {
    private val _categories = MutableStateFlow<List<MealCategory>>(emptyList())
    val categories = _categories.asStateFlow()
    private val _categoryMenuList = MutableStateFlow<List<MealCategoryRecipeDetail>>(emptyList())
    val categoryMenuList = _categoryMenuList.asStateFlow()
    private val _searchMeal = MutableStateFlow<List<MealDetails>>(emptyList())
    val searchMeal = _searchMeal.debounce(300)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _textSearch = MutableStateFlow("")
    val textSearch = _textSearch.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val res = try {
                repository.getMealCategories()
            } catch (e: Exception) {
                emptyList<MealCategory>()
                return@launch
            }
            _categories.update {
                res.category
            }
        }
    }

    fun updateTextSearch(data: String) = _textSearch.update {
        data
    }

    fun searchMealByName(name: String) = viewModelScope.launch(Dispatchers.IO) {
        name.takeIf {
            it != ""
        }?.run {
            _searchMeal.update {
                repository.searchMealByName(this).meals
            }
        }

    }

    fun searchRecipeByCategory(category: String, onError: (String) -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            val res = try {
                repository.getMealCategoryById(category)
            } catch (e: Exception) {
                onError(e.localizedMessage ?: "")
                return@launch
            }
            _categoryMenuList.update {
                res.meals
            }
        }
}