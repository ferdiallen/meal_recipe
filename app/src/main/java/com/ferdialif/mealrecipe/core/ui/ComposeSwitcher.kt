package com.ferdialif.mealrecipe.core.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun <T> ComposeSwitcher(
    data: T?,
    placeholder: @Composable () -> Unit,
    actualItem: @Composable () -> Unit
) {
    val isNotNull = remember(data) {
        data != null
    }
    Box{
        AnimatedVisibility(!isNotNull, enter = fadeIn(tween(100)), exit = fadeOut(tween())) {
            placeholder.invoke()
        }
        AnimatedVisibility(
            visible = isNotNull,
            enter = fadeIn(tween(200)),
            exit = fadeOut(tween(10))
        ) {
            actualItem.invoke()
        }

    }
}

@Composable
fun <T> ComposeSwitcher(
    data: List<T>?,
    placeholder: @Composable () -> Unit,
    actualItem: @Composable () -> Unit
) {
    val isNotNull = remember(data) {
        data != null
    }
    val isNotEmpty = remember(data) {
        data?.isNotEmpty()
    }

    AnimatedVisibility(
        !isNotNull && isNotEmpty != true,
        enter = fadeIn(tween(200)),
        exit = fadeOut(tween()), modifier = Modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            placeholder.invoke()
        }
    }
    AnimatedVisibility(
        visible = isNotNull && isNotEmpty == true,
        enter = fadeIn(tween(200)),
        exit = fadeOut(tween())
    ) {
        actualItem.invoke()
    }
}