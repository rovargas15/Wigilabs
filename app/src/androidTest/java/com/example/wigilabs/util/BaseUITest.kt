package com.example.wigilabs.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteractionCollection
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.wigilabs.HiltTestActivity
import com.example.wigilabs.ui.theme.WigilabsTheme
import dagger.hilt.android.testing.HiltAndroidRule
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import java.util.concurrent.TimeUnit

open class BaseUITest(private val dispatcherTest: Dispatcher) {

    @get:Rule(order = 1)
    val hiltRule by lazy { HiltAndroidRule(this) }

    @get:Rule(order = 2)
    val composeTestRule by lazy { createAndroidComposeRule<HiltTestActivity>() }

    private lateinit var mockServer: MockWebServer

    init {
        startMockServer()
    }

    @Before
    fun setup() {
        hiltRule.inject()
    }

    private fun startMockServer() {
        mockServer = MockWebServer().apply {
            dispatcher = dispatcherTest
            start(MOCK_WEB_SERVER_PORT)
        }
    }

    protected fun enqueueResponses(vararg response: MockResponse) {
        response.forEach { mockServer.enqueue(it) }
    }

    @After
    open fun tearDown() {
        mockServer.shutdown()
    }

    fun setMainContent(
        init: @Composable () -> Unit,
        block: () -> Unit
    ) {
        composeTestRule.setContent {
            WigilabsTheme {
                init()
            }
        }
        block()
    }

    private fun ComposeContentTestRule.waitUntilNodeCollectionCount(
        matcher: SemanticsNodeInteractionCollection,
        count: Int = 1,
        timeoutMillis: Long = TimeUnit.SECONDS.toMillis(5)
    ) {
        this.waitUntil(timeoutMillis) {
            matcher.fetchSemanticsNodes().size == count
        }
    }

    fun ComposeContentTestRule.waitUntilCollectionExists(
        matcher: SemanticsNodeInteractionCollection,
        timeoutMillis: Long = TimeUnit.SECONDS.toMillis(5)
    ) {
        return this.waitUntilNodeCollectionCount(matcher, 1, timeoutMillis)
    }

    fun ComposeContentTestRule.waitUntilCollectionDoesNotExist(
        matcher: SemanticsNodeInteractionCollection,
        timeoutMillis: Long = TimeUnit.SECONDS.toMillis(5)
    ) {
        return this.waitUntilNodeCollectionCount(matcher, 0, timeoutMillis)
    }

    private fun ComposeContentTestRule.waitUntilNodeCount(
        matcher: SemanticsMatcher,
        count: Int,
        timeoutMillis: Long = 1_000L
    ) {
        this.waitUntil(timeoutMillis) {
            this.onAllNodes(matcher).fetchSemanticsNodes().size == count
        }
    }

    fun ComposeContentTestRule.waitUntilExists(
        matcher: SemanticsMatcher,
        timeoutMillis: Long = 1_000L
    ) {
        return this.waitUntilNodeCount(matcher, 1, timeoutMillis)
    }

    fun ComposeContentTestRule.waitUntilDoesNotExist(
        matcher: SemanticsMatcher,
        timeoutMillis: Long = 1_000L
    ) {
        return this.waitUntilNodeCount(matcher, 0, timeoutMillis)
    }
}

private const val MOCK_WEB_SERVER_PORT = 8080
