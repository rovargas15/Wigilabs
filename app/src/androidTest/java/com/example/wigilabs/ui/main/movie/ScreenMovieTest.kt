package com.example.wigilabs.ui.main.movie

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.data.local.db.MovieDao
import com.example.data.local.entity.MovieEntity
import com.example.wigilabs.R
import com.example.wigilabs.ui.main.movie.screen.ScreenMovie
import com.example.wigilabs.ui.main.movie.viewmodel.MovieViewModel
import com.example.wigilabs.ui.utils.hiltViewModel
import com.example.wigilabs.util.BaseUITest
import com.example.wigilabs.util.FILE_SUCCESS_RESPONSE
import com.example.wigilabs.util.mockResponse
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.every
import kotlinx.coroutines.flow.flowOf
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.QueueDispatcher
import org.junit.Test
import org.junit.runner.RunWith
import java.net.HttpURLConnection
import javax.inject.Inject

@RunWith(AndroidJUnit4ClassRunner::class)
@HiltAndroidTest
class ScreenMovieTest : BaseUITest(dispatcherTest = QueueDispatcher()) {

    private val successResponse: MockResponse
        get() = mockResponse(FILE_SUCCESS_RESPONSE, HttpURLConnection.HTTP_OK)

    private val errorResponse: MockResponse
        get() = mockResponse(HttpURLConnection.HTTP_BAD_METHOD)

    @Inject
    lateinit var movieDao: MovieDao

    @Test
    fun showScreenMovieServiceSuccess() {
        every { movieDao.getAll() } returns flowOf(emptyList())
        enqueueResponses(successResponse)
        setMainContent(
            init = {
                val movieViewModel: MovieViewModel = hiltViewModel()
                ScreenMovie(
                    movieViewModel = movieViewModel,
                    onSelectMovie = {}
                )
            },
            block = {
                with(composeTestRule) {
                    // validate text top app bar
                    onNodeWithText(activity.getString(R.string.app_name)).assertIsDisplayed()

                    // validate loader
                    onNodeWithTag("ContentLoader").assertIsDisplayed()
                    waitUntilCollectionDoesNotExist(
                        matcher = onAllNodesWithTag("ContentLoader")
                    )

                    waitUntilCollectionExists(
                        matcher = onAllNodesWithTag("listMovie")
                    )
                    // Validate item 1
                    onNodeWithText("Fantastic Beasts: The Secrets of Dumbledore").assertIsDisplayed()
                    onNodeWithText("6.9").assertIsDisplayed()
                    // Validate item 2
                    onNodeWithText("Sonic the Hedgehog 2").assertIsDisplayed()
                    onNodeWithText("6.9").assertIsDisplayed()
                    // Validate item 3
                    onNodeWithText("The Lost City").assertIsDisplayed()
                    onNodeWithText("6.8").assertIsDisplayed()
                }
            }
        )
    }

    @Test
    fun showScreenMovieServiceError() {
        every { movieDao.getAll() } returns flowOf(emptyList())
        enqueueResponses(errorResponse)
        setMainContent(
            init = {
                val movieViewModel: MovieViewModel = hiltViewModel()
                ScreenMovie(
                    movieViewModel = movieViewModel,
                    onSelectMovie = {}
                )
            },
            block = {
                with(composeTestRule) {
                    waitUntilCollectionExists(
                        matcher = onAllNodesWithTag("ContentError")
                    )
                    onNodeWithText(activity.getString(R.string.search_message_error)).assertIsDisplayed()
                    onNodeWithText(activity.getString(R.string.btn_retry)).assertIsDisplayed()
                }
            }
        )
    }

    @Test
    fun showScreenMovieLocalSuccess() {
        every { movieDao.getAll() } returns flowOf(
            listOf(
                MovieEntity(
                    id = 1,
                    adult = false,
                    title = "The Lost City",
                    backdropPath = "/1Ds7xy7ILo8u2WWxdnkJth1jQVT.jp",
                    originalLanguage = "en",
                    originalTitle = "The Lost City",
                    overview = "test tes ",
                    popularity = 123456.1,
                    posterPath = "/neMZH82Stu91d3iqvLdNQfqPPyl.jpg",
                    releaseDate = "2022-03-24",
                    video = false,
                    voteAverage = 6.8,
                    voteCount = 1206
                )
            )
        )
        setMainContent(
            init = {
                val movieViewModel: MovieViewModel = hiltViewModel()
                ScreenMovie(
                    movieViewModel = movieViewModel,
                    onSelectMovie = {}
                )
            },
            block = {
                with(composeTestRule) {
                    // validate text top app bar
                    onNodeWithText(activity.getString(R.string.app_name)).assertIsDisplayed()
                    waitUntilCollectionExists(
                        matcher = onAllNodesWithTag("listMovie")
                    )
                    onNodeWithText("The Lost City").assertIsDisplayed()
                    onNodeWithText("6.8").assertIsDisplayed()
                }
            }
        )
    }
}
