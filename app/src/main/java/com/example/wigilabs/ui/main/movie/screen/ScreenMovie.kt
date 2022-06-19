package com.example.wigilabs.ui.main.movie.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.example.domain.model.Movie
import com.example.wigilabs.R
import com.example.wigilabs.ui.common.ContentLoader
import com.example.wigilabs.ui.common.CreateAnimation
import com.example.wigilabs.ui.common.LoadImage
import com.example.wigilabs.ui.common.TypeImage
import com.example.wigilabs.ui.main.movie.intent.MovieEvent
import com.example.wigilabs.ui.main.movie.state.MovieState
import com.example.wigilabs.ui.main.movie.viewmodel.MovieViewModel
import com.example.wigilabs.ui.theme.ColorGreyAlpha
import com.example.wigilabs.ui.theme.ColorRate
import com.example.wigilabs.ui.theme.LocalDimensions
import com.example.wigilabs.ui.theme.PrimaryColor
import com.example.wigilabs.ui.theme.Typography
import com.example.wigilabs.ui.theme.colorLightBackground
import com.example.wigilabs.ui.utils.hiltViewModel
import kotlinx.coroutines.launch

@Composable
fun ScreenMovie(onSelectMovie: (Movie) -> Unit) {
    TopBar(
        onSelectMovie = onSelectMovie
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    onSelectMovie: (Movie) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        color = colorLightBackground
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = PrimaryColor),
            )
        }
    ) { paddingValues ->
        ManagerMovieState(
            modifier = Modifier.padding(paddingValues),
            onSelectMovie = onSelectMovie,
        )
    }
}

@Composable
fun ManagerMovieState(
    modifier: Modifier,
    onSelectMovie: (Movie) -> Unit
) {
    val movieViewModel: MovieViewModel = hiltViewModel<MovieViewModel>().apply {
        process(MovieEvent.GetMovieALl)
    }
    val onEvent: (MovieEvent) -> Unit = { event: MovieEvent ->
        movieViewModel.process(event)
    }
    val state = movieViewModel.viewState.observeAsState()
    when (val value = state.value) {
        is MovieState.Loader -> {
            ContentLoader(modifier = modifier)
        }
        is MovieState.Success -> {
            CreateList(
                modifier = modifier,
                movies = value.movies,
                onSelectMovie = onSelectMovie
            )
        }
        is MovieState.Error -> {
            ContentError(modifier = modifier, onEvent = onEvent)
        }
    }
}

@Composable
fun CreateList(
    modifier: Modifier,
    movies: List<Movie>,
    onSelectMovie: (Movie) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = LocalDimensions.current.minSizeCard),
        horizontalArrangement = Arrangement.spacedBy(space = LocalDimensions.current.paddingMedium),
        verticalArrangement = Arrangement.spacedBy(space = LocalDimensions.current.paddingMedium),
        modifier = modifier.padding(
            all = LocalDimensions.current.paddingMedium,
        )
            .testTag("listMovie")
    ) {
        items(movies.size) { index ->
            CardMovie(movies[index], onSelectMovie)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardMovie(movie: Movie, onSelectMovie: (Movie) -> Unit) {
    OutlinedCard(
        modifier = Modifier.height(LocalDimensions.current.heightCard)
            .clickable {
                onSelectMovie(movie)
            }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomStart
        ) {

            LoadImage(
                url = movie.posterPath,
                type = TypeImage.Medium,
                modifier = Modifier.fillMaxSize()
            )

            Column(
                modifier = Modifier
                    .background(ColorGreyAlpha)
                    .wrapContentHeight()
                    .fillMaxWidth(),
                verticalArrangement = Arrangement
                    .spacedBy(LocalDimensions.current.paddingSmall, Alignment.Bottom),

            ) {
                Text(
                    text = movie.title,
                    style = Typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(start = LocalDimensions.current.paddingMedium),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Row(
                    modifier = Modifier.padding(start = LocalDimensions.current.paddingMedium)
                ) {

                    Text(
                        text = movie.voteAverage.toString(),
                        style = Typography.bodyMedium,
                    )

                    Icon(
                        imageVector = Icons.Outlined.Star,
                        contentDescription = null,
                        tint = ColorRate
                    )
                }
            }
        }
    }
}

@Composable
fun ContentError(
    modifier: Modifier,
    onEvent: (MovieEvent) -> Unit
) {
    val scope = rememberCoroutineScope()
    Column(
        modifier = modifier.fillMaxSize().testTag("ContentError"),
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

        TextButton(
            onClick = {
                scope.launch {
                    onEvent(MovieEvent.Reload)
                }
            }
        ) {
            Text(
                text = stringResource(id = R.string.btn_retry),
                style = Typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
