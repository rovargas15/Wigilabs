package com.example.wigilabs.ui.main.detail

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.data.local.db.MovieDao
import com.example.data.local.entity.MovieEntity
import com.example.wigilabs.R
import com.example.wigilabs.ui.main.navigation.AppNavigation
import com.example.wigilabs.ui.main.navigation.Route
import com.example.wigilabs.util.BaseUITest
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.every
import kotlinx.coroutines.flow.flowOf
import okhttp3.mockwebserver.QueueDispatcher
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4ClassRunner::class)
@HiltAndroidTest
class DetailMovieScreenTest : BaseUITest(dispatcherTest = QueueDispatcher()) {

    @Inject
    lateinit var movieDao: MovieDao

    @Test
    fun showScreenMovieDetailSuccess() {
        every { movieDao.getMovieById(1) } returns flowOf(
            MovieEntity(
                id = 1,
                adult = false,
                title = "Fantastic Beasts: The Secrets of Dumbledore",
                backdropPath = "/zGLHX92Gk96O1DJvLil7ObJTbaL.jp",
                originalLanguage = "en",
                originalTitle = "Fantastic Beasts: The Secrets of Dumbledore",
                overview = "Professor Albus Dumbledore knows the powerful",
                popularity = 4188.23,
                posterPath = "/egoyMDLqCxzjnSrWOz50uLlJWmD.jpg",
                releaseDate = "2022-04-06",
                video = false,
                voteAverage = 6.9,
                voteCount = 1841
            )
        )
        setMainContent(
            init = {
                val navController = rememberNavController()
                AppNavigation(navController = navController)
                navController.navigate("${Route.MOVIE_DETAIL}1")
            },
            block = {
                with(composeTestRule) {
                    // validate text top app bar
                    onNodeWithText(activity.getString(R.string.app_name)).assertIsDisplayed()

                    waitUntilExists(
                        matcher = hasText("Fantastic Beasts: The Secrets of Dumbledore")
                    )
                    onNodeWithText("Professor Albus Dumbledore knows the powerful").assertIsDisplayed()
                    onNodeWithText("6.9").assertIsDisplayed()
                    onNodeWithText("2022-04-06").assertIsDisplayed()
                }
            }
        )
    }
}
