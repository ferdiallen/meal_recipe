package com.ferdialif.mealrecipe.core.navigation

sealed class NavigationIdentifier(val route:String) {
    object HomeScreen:NavigationIdentifier("home_screen")
    object DetailScreen:NavigationIdentifier("detail_screen")
    object Favorite:NavigationIdentifier("favorite_screen")
}