package com.ferdialif.mealrecipe.presentation.detail

import android.annotation.SuppressLint
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.ferdialif.mealrecipe.core.fonts.poppins
import com.ferdialif.mealrecipe.core.ui.ComposeSwitcher
import com.ferdialif.mealrecipe.core.utils.combineIngredients
import com.ferdialif.mealrecipe.core.utils.toYoutubeEmbedded
import com.ferdialif.mealrecipe.domain.model.MealCategoryRecipeDetail
import com.ferdialif.mealrecipe.domain.model.toFavoriteMeal
import com.ferdialif.mealrecipe.ui.theme.GreenColorTheme
import kotlinx.coroutines.launch

val listChipMenu = listOf(
    "How to Cook", "Ingredients"
)

@SuppressLint("SetJavaScriptEnabled")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = hiltViewModel(),
    mealId: String = "",
    onSaveFavorite: (MealCategoryRecipeDetail?, Boolean) -> Unit
) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 2 })
    val menuPagerState = rememberPagerState(initialPage = 0, pageCount = { 2 })
    LaunchedEffect(key1 = Unit, block = {
        viewModel.retrieveMealRecipe(mealId)
    })
    val isBookmarked by viewModel.isBookmarked.collectAsState()
    val recipe by viewModel.mealDetailsRecipe.collectAsState(null)
    val scope = rememberCoroutineScope()
    val currentSelected by remember {
        derivedStateOf {
            menuPagerState.currentPage
        }
    }
    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {
            onSaveFavorite(
                recipe?.toFavoriteMeal(),
                !isBookmarked
            )
        }) {
            Icon(
                imageVector = if (isBookmarked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                contentDescription = "", tint = if (isBookmarked) Color.Red else Color.Black
            )
        }
    }) { it ->
        it
        Column(modifier = modifier.navigationBarsPadding()) {
            Card(
                shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .wrapContentSize(), contentAlignment = Alignment.BottomCenter
                ) {

                    HorizontalPager(state = pagerState, modifier = Modifier.fillMaxHeight(0.5F)) {
                        when (it) {
                            0 -> {
                                AsyncImage(
                                    model = recipe?.strMealThumb,
                                    contentDescription = "meal_image",
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    contentScale = ContentScale.Crop
                                )
                            }

                            1 -> {
                                var isBuffering by remember {
                                    mutableStateOf(false)
                                }
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    AndroidView(
                                        factory = { context ->
                                            WebView(context).apply {
                                                loadData(
                                                    recipe?.strYoutube?.toYoutubeEmbedded() ?: "",
                                                    "text/html",
                                                    "utf-8"
                                                )
                                                settings.javaScriptEnabled = true
                                                layoutParams = LinearLayout.LayoutParams(
                                                    MATCH_PARENT,
                                                    MATCH_PARENT
                                                )
                                                webViewClient = object : WebViewClient() {
                                                    override fun shouldOverrideUrlLoading(
                                                        view: WebView?,
                                                        request: WebResourceRequest?
                                                    ): Boolean {
                                                        return false
                                                    }
                                                }
                                                webChromeClient = object : WebChromeClient() {
                                                    override fun onProgressChanged(
                                                        view: WebView?,
                                                        newProgress: Int
                                                    ) {
                                                        isBuffering = newProgress < 100
                                                    }
                                                }
                                            }
                                        }, modifier = Modifier
                                            .fillMaxSize()
                                            .clip(RoundedCornerShape(12.dp)),
                                        onReset = { webview ->
                                            webview.loadUrl("")
                                        },
                                        update = { webview ->
                                            webview.loadData(
                                                recipe?.strYoutube?.toYoutubeEmbedded() ?: "",
                                                "text/html",
                                                "utf-8"
                                            )
                                        }
                                    )
                                    if (isBuffering) {
                                        CircularProgressIndicator(color = GreenColorTheme)
                                    }
                                }
                            }
                        }

                    }
                    //
                    LazyRow(
                        modifier = Modifier.padding(bottom = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(pagerState.pageCount) {
                            Card(
                                modifier = Modifier.size(10.dp), shape = CircleShape,
                                colors = CardDefaults.cardColors(
                                    containerColor = if (it == pagerState.currentPage)
                                        GreenColorTheme else Color.White
                                )
                            ) {

                            }
                        }
                    }
                }

            }
            Spacer(modifier = Modifier.height(20.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                ComposeSwitcher(
                    data = recipe,
                    placeholder = { CircularProgressIndicator(color = GreenColorTheme) },
                    actualItem = {
                        Text(
                            text = recipe?.strMeal ?: "",
                            fontFamily = poppins,
                            style = TextStyle(fontSize = 34.sp)
                        )
                    })
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(0.5F), shape = RoundedCornerShape(32.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {}
                    }
                    Card(
                        modifier = Modifier
                            .fillMaxSize(), shape = RoundedCornerShape(32.dp),
                        elevation = CardDefaults.cardElevation(1.dp),
                        colors = CardDefaults.cardColors(Color.White)
                    ) {
                        Row(modifier = Modifier.fillMaxSize()) {
                            listChipMenu.forEachIndexed { index, data ->
                                PagerChip(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .weight(1F),
                                    name = data,
                                    onSelected = {
                                        scope.launch {
                                            menuPagerState.animateScrollToPage(index)
                                        }
                                    },
                                    currentSelected = {
                                        currentSelected == index
                                    })

                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                HorizontalPager(state = menuPagerState) {
                    when (it) {
                        0 -> {
                            Text(
                                text = recipe?.strInstructions ?: "",
                                fontFamily = poppins, fontWeight = FontWeight.Light
                            )
                        }

                        1 -> {
                            LazyHorizontalStaggeredGrid(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                rows = StaggeredGridCells.Fixed(5),
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                                horizontalItemSpacing = 12.dp,
                                userScrollEnabled = false
                            ) {
                                items(recipe?.combineIngredients() ?: emptyList()) { data ->
                                    IngredientsChip(name = data?.takeIf { it != "" }
                                        ?: return@items)
                                }
                            }
                        }
                    }
                }
            }
        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PagerChip(
    modifier: Modifier = Modifier,
    name: String = "",
    currentSelected: () -> Boolean,
    onSelected: () -> Unit
) {
    Card(
        modifier = modifier
            .padding(4.dp), shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(if (currentSelected()) GreenColorTheme else Color.White),
        onClick = {
            onSelected()
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = name,
                fontFamily = poppins,
                color = if (currentSelected()) Color.White else Color.Black
            )
        }
    }
}

@Composable
fun IngredientsChip(name: String) {
    Card(
        modifier = Modifier.height(100.dp),
        colors = CardDefaults.cardColors(GreenColorTheme),
        shape = RoundedCornerShape(32.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = name, fontFamily = poppins,
                modifier = Modifier.padding(horizontal = 12.dp),
                color = Color.White
            )
        }
    }
}