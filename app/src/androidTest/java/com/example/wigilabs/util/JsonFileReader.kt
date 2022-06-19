package com.example.wigilabs.util

import androidx.test.platform.app.InstrumentationRegistry
import okhttp3.mockwebserver.MockResponse
import java.io.BufferedReader
import java.io.Reader
import java.util.concurrent.TimeUnit

fun mockResponse(code: Int): MockResponse =
    MockResponse()
        .setBodyDelay(DELAY_RESPONSE, TimeUnit.SECONDS)
        .setResponseCode(code)

fun mockResponse(fileName: String, responseCode: Int): MockResponse =
    MockResponse()
        .setBodyDelay(DELAY_RESPONSE, TimeUnit.SECONDS)
        .setResponseCode(responseCode)
        .setBody(getJson(fileName))

private fun getJson(path: String): String {
    var content: String
    val testContext = InstrumentationRegistry.getInstrumentation().context
    val inputStream = testContext.assets.open(path)
    val reader = BufferedReader(inputStream.reader() as Reader)
    reader.use { bufferedReader ->
        content = bufferedReader.readText()
    }
    return content
}

private const val DELAY_RESPONSE = 3L
