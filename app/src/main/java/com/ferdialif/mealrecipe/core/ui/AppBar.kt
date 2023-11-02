package com.ferdialif.mealrecipe.core.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ferdialif.mealrecipe.R
import com.ferdialif.mealrecipe.core.fonts.poppins
import com.ferdialif.mealrecipe.ui.theme.GreenColorTheme

@Composable
fun CustomAppBar(
    shouldShowBackButton: () -> Boolean,
    onBackClick: () -> Unit,
    tint: Color = Color.White
) {
    Row(
        modifier = Modifier
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AnimatedVisibility(visible = shouldShowBackButton()) {
            IconButton(onClick = { onBackClick() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "",
                    tint = tint
                )
            }

        }
    }

}

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    text: () -> String,
    onTextChange: (String) -> Unit,
    currentFocusState: (Boolean) -> Unit
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        val focus = LocalFocusManager.current
        var hasFocus by remember {
            mutableStateOf(false)
        }
        AnimatedVisibility(visible = hasFocus) {
            IconButton(onClick = {
                focus.clearFocus()
            }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "")
            }
        }
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    hasFocus = it.hasFocus
                    currentFocusState(it.hasFocus)
                },
            value = text(),
            onValueChange = onTextChange::invoke,
            shape = RoundedCornerShape(32.dp),
            trailingIcon = {
                IconButton(onClick = { }) {
                    Icon(imageVector = Icons.Filled.Search, contentDescription = "")
                }
            },
            placeholder = {
                Text(
                    text = stringResource(R.string.search),
                    fontFamily = poppins,
                    color = Color.Black
                )
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = GreenColorTheme,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.LightGray.copy(0.5F)
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            textStyle = TextStyle(fontFamily = poppins, fontWeight = FontWeight.Light)
        )

    }
}