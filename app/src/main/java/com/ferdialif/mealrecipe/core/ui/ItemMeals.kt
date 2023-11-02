package com.ferdialif.mealrecipe.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ferdialif.mealrecipe.core.fonts.poppins
import com.ferdialif.mealrecipe.ui.theme.GreenColorTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemMeals(
    isBookmarkMode: Boolean = false,
    name: String = "",
    image: String = "",
    onClickItem: () -> Unit
) {
    Card(
        modifier = if (!isBookmarkMode) Modifier
            .width(200.dp)
            .fillMaxHeight()
        else Modifier
            .fillMaxWidth()
            .height(300.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        onClick = {
            onClickItem()
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = image,
                contentDescription = "",
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.17F), shape = RoundedCornerShape(0),
                    colors = CardDefaults.cardColors(
                        GreenColorTheme
                    )
                ) {
                    Text(
                        text = name,
                        fontFamily = poppins,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .padding(top = 12.dp), maxLines = 1
                    )
                }
            }
        }
    }
}