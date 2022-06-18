package com.example.wigilabs.ui.main.detail.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.example.domain.model.Movie
import com.example.wigilabs.R
import com.example.wigilabs.ui.common.ContentLoader
import com.example.wigilabs.ui.common.CreateAnimation
import com.example.wigilabs.ui.common.LoadImage
import com.example.wigilabs.ui.common.TypeImage
import com.example.wigilabs.ui.main.detail.state.DetailMovieState
import com.example.wigilabs.ui.main.detail.viewmodel.DetailMovieViewModel
import com.example.wigilabs.ui.theme.ColorRate
import com.example.wigilabs.ui.theme.LocalDimensions
import com.example.wigilabs.ui.theme.PrimaryColor
import com.example.wigilabs.ui.theme.Typography
import com.example.wigilabs.ui.theme.colorLightBackground

@Composable
fun DetailMovieScreen(
    detailMovieViewModel: DetailMovieViewModel,
    onBackPressedCallback: () -> Unit
) {
    TopBar(
        detailMovieViewModel = detailMovieViewModel,
        onBackPressedCallback = onBackPressedCallback
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    detailMovieViewModel: DetailMovieViewModel,
    onBackPressedCallback: () -> Unit
) {
    Scaffold(
        topBar = {
            SmallTopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = PrimaryColor),
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        color = colorLightBackground
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onBackPressedCallback() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null,
                            tint = colorLightBackground
                        )
                    }
                }
            )
        },
    ) { padding: PaddingValues ->
        ManagerDetailMovieState(
            detailMovieViewModel = detailMovieViewModel,
            modifier = Modifier.padding(padding),
        )
    }
}

@Composable
fun ManagerDetailMovieState(
    detailMovieViewModel: DetailMovieViewModel,
    modifier: Modifier
) {
    val state = detailMovieViewModel.viewState.observeAsState()
    when (val value = state.value) {
        is DetailMovieState.Loader -> {
            ContentLoader(modifier = modifier)
        }
        is DetailMovieState.Success -> {
            ShowDetailMovie(
                movie = value.movie,
                modifier = modifier
            )
        }
        is DetailMovieState.Error -> {
            ContentError(modifier = modifier)
        }
    }
}

@Composable
fun ShowDetailMovie(movie: Movie, modifier: Modifier) {
    ConstraintLayout(modifier = modifier) {
        val (header, poster, title, voteCnl, overview) = createRefs()

        LoadImage(
            url = movie.backdropPath, type = TypeImage.Large,
            modifier = Modifier.height(
                LocalDimensions.current.imageMedium
            ).constrainAs(header) {}
        )

        LoadImage(
            url = movie.posterPath, type = TypeImage.Medium,
            modifier = Modifier.height(
                LocalDimensions.current.heightMedium
            ).width(LocalDimensions.current.imageSmall).constrainAs(poster) {
                top.linkTo(header.bottom)
                bottom.linkTo(header.bottom)
                start.linkTo(header.start, 10.dp)
            }
        )

        Text(
            text = movie.originalTitle,
            style = Typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .padding(start = LocalDimensions.current.paddingMedium)
                .constrainAs(title) {
                    start.linkTo(poster.end, 4.dp)
                    end.linkTo(header.end, 10.dp)
                    top.linkTo(header.bottom)
                    bottom.linkTo(poster.bottom)
                    width = Dimension.fillToConstraints
                },
        )

        Row(
            modifier = Modifier
                .constrainAs(voteCnl) {
                    top.linkTo(poster.bottom, 10.dp)
                    start.linkTo(poster.start)
                    end.linkTo(header.end)
                    width = Dimension.fillToConstraints
                },
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ConstraintLayout() {
                val (vote, star) = createRefs()

                Text(
                    text = movie.voteAverage.toString(),
                    style = Typography.titleLarge,
                    modifier = Modifier.padding(start = LocalDimensions.current.paddingMedium)
                        .constrainAs(vote) {
                            start.linkTo(parent.start)
                        }
                )

                Icon(
                    imageVector = Icons.Outlined.Star,
                    contentDescription = null,
                    tint = ColorRate,
                    modifier = Modifier.constrainAs(star) {
                        start.linkTo(vote.end)
                        top.linkTo(vote.top)
                        bottom.linkTo(vote.bottom)
                    }
                )
            }

            Text(
                text = movie.releaseDate,
                style = Typography.titleLarge,
                modifier = Modifier.padding(start = LocalDimensions.current.paddingMedium)
            )
        }

        Text(
            text = movie.overview,
            style = Typography.bodyMedium,
            textAlign = TextAlign.Justify,
            modifier = Modifier.constrainAs(overview) {
                top.linkTo(voteCnl.bottom, 10.dp)
                start.linkTo(poster.start)
                end.linkTo(header.end, 10.dp)
                width = Dimension.fillToConstraints
            }
        )
    }
}

@Composable
fun ContentError(
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("ContentError"),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            LocalDimensions.current.paddingSmall,
            alignment = Alignment.CenterVertically
        ),
    ) {
        CreateAnimation(raw = LottieCompositionSpec.RawRes(R.raw.error))
        Text(
            text = stringResource(id = R.string.search_message_error),
            style = Typography.bodyMedium,
        )
    }
}
