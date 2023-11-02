package com.ferdialif.mealrecipe.presentation.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ferdialif.mealrecipe.domain.model.MealCategoryRecipeDetail
import com.ferdialif.mealrecipe.domain.repository.MealRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookMarkViewModel @Inject constructor(
    private val repository: MealRepository
) : ViewModel() {
    private val _bookmarkList = MutableStateFlow<List<MealCategoryRecipeDetail>>(emptyList())
    val bookmarkList = _bookmarkList.asStateFlow()

    init {
        loadBookmarkData()
    }

    private fun loadBookmarkData() = viewModelScope.launch(Dispatchers.IO) {
        val res = repository.listBookmark()
        res.collectLatest {
            _bookmarkList.tryEmit(it)
        }
    }
}