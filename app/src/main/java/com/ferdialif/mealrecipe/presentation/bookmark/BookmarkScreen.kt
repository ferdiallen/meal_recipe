package com.ferdialif.mealrecipe.presentation.bookmark

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ferdialif.mealrecipe.R
import com.ferdialif.mealrecipe.core.fonts.poppins
import com.ferdialif.mealrecipe.core.ui.CustomAppBar
import com.ferdialif.mealrecipe.core.ui.ItemMeals

@Composable
fun BookmarkScreen(
    modifier: Modifier = Modifier,
    viewModel: BookMarkViewModel = hiltViewModel(),
    popBack: () -> Unit,
    navigateToDetailScreen: (String) -> Unit
) {
    val bookmarkData by viewModel.bookmarkList.collectAsState()
    val isDataEmpty = remember(bookmarkData.size) {
        bookmarkData.isEmpty()
    }
    Column(
        modifier.padding(horizontal = 12.dp)
    ) {
        CustomAppBar(shouldShowBackButton = { true }, onBackClick = {
            popBack()
        }, tint = Color.Black)
        if (!isDataEmpty) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(bookmarkData) {
                    ItemMeals(isBookmarkMode = true,
                        it.strMeal,
                        image = it.strMealThumb,
                        onClickItem = {
                            navigateToDetailScreen(it.idMeal)
                        }
                    )
                }
            }

        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = stringResource(id = R.string.no_menu_found),
                    fontFamily = poppins,
                    fontWeight = FontWeight.Light
                )


            }

        }
    }

}