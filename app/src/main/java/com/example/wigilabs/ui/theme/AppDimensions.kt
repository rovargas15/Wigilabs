package com.example.wigilabs.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class AppDimensions(
    val paddingSmall: Dp = 4.dp,
    val paddingMedium: Dp = 8.dp,
    val paddingLarge: Dp = 12.dp,
    val paddingXLarge: Dp = 16.dp,
    val borderSmall: Dp = 1.dp,
    val imageSmall: Dp = 150.dp,
    val imageMedium: Dp = 250.dp,
    val heightCard: Dp = 270.dp,
    val heightMedium: Dp = 200.dp,
    val minSizeCard: Dp = 150.dp
)

internal val LocalDimensions = staticCompositionLocalOf { AppDimensions() }
