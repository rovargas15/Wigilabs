package com.example.wigilabs.ui.main.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.domain.model.Movie
import com.example.wigilabs.ui.main.detail.screen.DetailMovieScreen
import com.example.wigilabs.ui.main.detail.viewmodel.DetailMovieViewModel
import com.example.wigilabs.ui.main.movie.screen.ScreenMovie
import com.example.wigilabs.ui.main.movie.viewmodel.MovieViewModel
import com.example.wigilabs.ui.utils.hiltViewModel

@Composable
fun AppNavigation(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Graph.MAIN_GRAPH) {
        mainGraph(
            navController = navController
        )
    }
}

@SuppressLint("RememberReturnType")
fun NavGraphBuilder.mainGraph(
    navController: NavHostController
) {
    navigation(startDestination = Route.MOVIE, route = Graph.MAIN_GRAPH) {
        composable(route = Route.MOVIE) {
            val movieViewModel: MovieViewModel = hiltViewModel()
            ScreenMovie(movieViewModel) { movie: Movie ->
                navController.navigate("${Route.MOVIE_DETAIL}${movie.id}")
            }
        }

        composable(
            route = "${Route.MOVIE_DETAIL}{${Parameter.MOVIE_ID}}",
            arguments = listOf(
                navArgument(Parameter.MOVIE_ID) { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val detailMovieViewModel: DetailMovieViewModel = hiltViewModel(backStackEntry)
            DetailMovieScreen(detailMovieViewModel) {
                navController.popBackStack()
            }
        }
    }
}

object Graph {
    const val MAIN_GRAPH = "main_graph"
}

object Route {
    const val MOVIE = "movie"
    const val MOVIE_DETAIL = "detail/"
}

object Parameter {
    const val MOVIE_ID = "movieId"
}
