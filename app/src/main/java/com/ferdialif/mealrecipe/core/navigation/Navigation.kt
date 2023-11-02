package com.ferdialif.mealrecipe.core.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ferdialif.mealrecipe.core.ui.CustomAppBar
import com.ferdialif.mealrecipe.presentation.bookmark.BookmarkScreen
import com.ferdialif.mealrecipe.presentation.detail.DetailScreen
import com.ferdialif.mealrecipe.presentation.home.HomeScreen

@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    controller: NavHostController = rememberNavController(),
    viewModel: NavigationViewModel = hiltViewModel()
) {
    val navStack by controller.currentBackStackEntryAsState()
    NavHost(
        navController = controller,
        startDestination = NavigationIdentifier.HomeScreen.route
    ) {
        composable(route = NavigationIdentifier.HomeScreen.route) {
            HomeScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
                    .statusBarsPadding(),
                navigateToDetailScreen = {
                    controller.navigate(NavigationIdentifier.DetailScreen.route + "/$it")
                }, navigateToFavoriteScreen = {
                    controller.navigate(NavigationIdentifier.Favorite.route)
                }
            )
        }
        composable(
            route = NavigationIdentifier.DetailScreen.route + "/{mealId}",
            arguments = listOf(navArgument("mealId") {
                this.defaultValue = ""
                type = NavType.StringType
            })
        ) {
            val mealId = it.arguments?.getString("mealId") ?: ""
            DetailScreen(
                modifier = Modifier
                    .fillMaxSize(),
                mealId = mealId, onSaveFavorite = { data, isSaved ->
                    if (isSaved) {
                        viewModel.insertToFavorite(data ?: return@DetailScreen)
                    } else {
                        viewModel.removeFromFavorite(data ?: return@DetailScreen)
                    }
                }
            )
        }
        composable(route = NavigationIdentifier.Favorite.route) {
            BookmarkScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding(),
                popBack = {
                    controller.popBackStack()
                }, navigateToDetailScreen = {idMeal->
                    controller.navigate(NavigationIdentifier.DetailScreen.route + "/$idMeal")
                }
            )
        }
    }
    Box(modifier = modifier.statusBarsPadding()) {
        CustomAppBar(
            shouldShowBackButton = { navStack?.destination?.route?.contains(NavigationIdentifier.DetailScreen.route) == true },
            onBackClick = { controller.popBackStack() }
        )
    }

}