package com.ferdialif.mealrecipe.presentation.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.ferdialif.mealrecipe.R
import com.ferdialif.mealrecipe.core.fonts.poppins
import com.ferdialif.mealrecipe.core.ui.ComposeSwitcher
import com.ferdialif.mealrecipe.core.ui.CustomTextField
import com.ferdialif.mealrecipe.core.ui.ItemMeals
import com.ferdialif.mealrecipe.ui.theme.GreenColorTheme
import com.ferdialif.mealrecipe.ui.theme.InactiveCardColor

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToDetailScreen: (String) -> Unit,
    navigateToFavoriteScreen: () -> Unit
) {
    val categories by viewModel.categories.collectAsState()
    val menuCategory by viewModel.categoryMenuList.collectAsState()
    val searchMenu by viewModel.searchMeal.collectAsState()
    val state = rememberLazyListState()
    val textSearch by viewModel.textSearch.collectAsState()
    var currentFocusTextField by remember {
        mutableStateOf(false)
    }
    Column(modifier = modifier) {
        AnimatedVisibility(visible = !currentFocusTextField) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1F)) {
                    Text(
                        text = stringResource(R.string.hi_title),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = poppins
                    )
                    Text(
                        text = stringResource(R.string.plan_your_meal_here_title),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.LightGray,
                        fontFamily = poppins
                    )
                    Text(
                        text = stringResource(R.string.recommended_meals_today_title),
                        fontWeight = FontWeight.SemiBold,

                        fontFamily = poppins, style = TextStyle(fontSize = 28.sp)
                    )
                }
                Card(
                    shape = CircleShape,
                    elevation = CardDefaults.cardElevation(4.dp),
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background)
                ) {
                    IconButton(
                        onClick = { navigateToFavoriteScreen() }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "",
                            tint = Color.Red
                        )
                    }

                }

            }

        }
        Spacer(modifier = Modifier.height(6.dp))
        CustomTextField(
            modifier = Modifier.fillMaxWidth(),
            text = { textSearch },
            onTextChange = {
                viewModel.updateTextSearch(it)
                viewModel.searchMealByName(it)
            }, currentFocusState = {
                currentFocusTextField = it
            })
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier
                .fillMaxHeight(0.5F), state = state
        ) {
            if (currentFocusTextField || textSearch != "") {
                itemsIndexed(searchMenu) { index, data ->
                    ComposeSwitcher(
                        data = menuCategory,
                        placeholder = { CircularProgressIndicator(color = GreenColorTheme) },
                        actualItem = {
                            ItemMeals(onClickItem = {
                                navigateToDetailScreen(data.idMeal ?: "")
                            }, name = data.strMeal ?: "", image = data.strMealThumb ?: "")
                        })
                }
                if (searchMenu.isEmpty() && textSearch != "") {
                    item {
                        Text(
                            text = stringResource(R.string.no_menu_found),
                            fontFamily = poppins,
                            fontWeight = FontWeight.Light
                        )
                    }
                }
            } else {
                itemsIndexed(menuCategory) { index, data ->
                    ComposeSwitcher(
                        data = menuCategory,
                        placeholder = { CircularProgressIndicator(color = GreenColorTheme) },
                        actualItem = {
                            ItemMeals(onClickItem = {
                                navigateToDetailScreen(data.idMeal)
                            }, name = data.strMeal, image = data.strMealThumb)
                        })
                }

            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        var currentItemSelected: Int? by remember {
            mutableStateOf(0)
        }
        LaunchedEffect(key1 = currentItemSelected, key2 = categories.size, block = {
            if (categories.isNotEmpty()) {
                val findCategory = categories[currentItemSelected ?: 0].strCategory
                viewModel.searchRecipeByCategory(findCategory ?: "", onError = {

                })
                state.animateScrollToItem(0)
            }
        })
        Text(
            text = stringResource(R.string.categories),
            fontFamily = poppins,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(6.dp))
        AnimatedVisibility(visible = !currentFocusTextField) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(start = 4.dp, end = 4.dp)
            ) {
                itemsIndexed(categories) { index, data ->
                    ItemCategory(
                        name = data.strCategory ?: "",
                        image = data.strCategoryThumb ?: "",
                        currentSelected = {
                            currentItemSelected == index
                        },
                        selectedIndex = {
                            currentItemSelected = index
                        }
                    )
                }
            }

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ItemCategory(
    name: String = "",
    image: String = "",
    selectedIndex: () -> Unit,
    currentSelected: () -> Boolean
) {
    val animateBackgroundSelected by animateColorAsState(
        targetValue = if (currentSelected())
            GreenColorTheme else InactiveCardColor, label = ""
    )
    Card(
        modifier = Modifier
            .width(100.dp)
            .height(120.dp),
        onClick = {
            selectedIndex()
        },
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (currentSelected()) 6.dp else 1.dp
        ), colors = CardDefaults.cardColors(containerColor = animateBackgroundSelected)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = image,
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = name,
                color = if (currentSelected()) Color.White else Color.Black,
                fontFamily = poppins,
                fontSize = 14.sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
}

