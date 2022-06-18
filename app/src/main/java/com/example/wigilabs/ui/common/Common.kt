package com.example.wigilabs.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import coil.request.ImageRequest.Builder
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.wigilabs.R
import com.example.wigilabs.ui.theme.LocalDimensions

enum class TypeImage(val value: String) {
    Origin("original"),
    Large("w400"),
    Medium("w200")
}

@Composable
fun LoadImage(url: String, type: TypeImage, modifier: Modifier = Modifier) {
    val urlBase = stringResource(id = R.string.base_url_image, type.value)
    AsyncImage(
        model = Builder(LocalContext.current)
            .data(urlBase + url)
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.ic_placeholder_movie),
        contentDescription = null,
        modifier = modifier,
        contentScale = ContentScale.FillBounds
    )
}

@Composable
fun ContentLoader(modifier: Modifier) {
    Box(
        modifier = modifier.fillMaxSize().testTag("ContentLoader"),
        contentAlignment = Alignment.Center
    ) {
        CreateAnimation(raw = LottieCompositionSpec.RawRes(R.raw.loader))
    }
}

@Composable
fun CreateAnimation(
    raw: LottieCompositionSpec.RawRes,
    modifier: Modifier = Modifier
) {
    val composition by rememberLottieComposition(spec = raw)
    LottieAnimation(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        modifier = modifier.size(LocalDimensions.current.imageSmall)
    )
}
