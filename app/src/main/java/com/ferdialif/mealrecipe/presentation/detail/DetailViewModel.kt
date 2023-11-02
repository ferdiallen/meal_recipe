package com.ferdialif.mealrecipe.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ferdialif.mealrecipe.domain.model.MealDetails
import com.ferdialif.mealrecipe.domain.model.toFavoriteMeal
import com.ferdialif.mealrecipe.domain.repository.MealRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: MealRepository
) : ViewModel() {
    private var _mealDetailsRecipe = MutableSharedFlow<MealDetails?>(5)
    val mealDetailsRecipe = _mealDetailsRecipe.asSharedFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val isBookmarked = repository.listBookmark().flatMapLatest {
        flow {
            while (true) {
                emit(it)
                kotlinx.coroutines.delay(500)
            }
        }
    }.combine(mealDetailsRecipe) { flow1, flow2 ->
            val res = flow1.contains(flow2?.toFavoriteMeal())
        res
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun retrieveMealRecipe(mealId: String) = viewModelScope.launch(Dispatchers.IO) {
        val latestData = repository.getMealDetails(mealId)
        _mealDetailsRecipe.emit(latestData.meals.firstOrNull())
    }

}