package com.ferdialif.mealrecipe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.ferdialif.mealrecipe.core.navigation.Navigation
import com.ferdialif.mealrecipe.presentation.bookmark.BookmarkScreen
import com.ferdialif.mealrecipe.presentation.home.HomeScreen
import com.ferdialif.mealrecipe.ui.theme.MealRecipeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            MealRecipeTheme {
                 Navigation()
            }
        }
    }
}