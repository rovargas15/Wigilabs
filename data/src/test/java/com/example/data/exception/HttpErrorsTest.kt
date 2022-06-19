package com.example.data.exception

import com.example.domain.exception.BadRequestException
import com.example.domain.exception.HttpErrorCode
import com.example.domain.exception.InternalErrorException
import com.example.domain.exception.NotFoundException
import com.example.domain.exception.Unauthorized
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.HttpException

class HttpErrorsTest {

    private val exception: HttpException = mockk(relaxed = true)
    private val httpErrors = HttpErrors()

    @Test
    fun giveExceptionWhenGetHttpErrorThenReturnBadRequestException() {
        // Give
        every { exception.code() } answers { 400 }

        // When
        val result = httpErrors.getHttpError(exception)

        // Then
        assertEquals(result, BadRequestException)

        verify(exactly = 2) { exception.code() }
        confirmVerified(exception)
    }

    @Test
    fun giveExceptionWhenGetHttpErrorThenReturnNotFoundException() {
        // Give
        every { exception.code() } answers { 404 }

        // When
        val result = httpErrors.getHttpError(exception)

        // Then
        assertEquals(result, NotFoundException)

        verify(exactly = 2) { exception.code() }
        confirmVerified(exception)
    }

    @Test
    fun giveExceptionWhenGetHttpErrorThenReturnInternalErrorException() {
        // Give
        every { exception.code() } answers { 500 }

        // When
        val result = httpErrors.getHttpError(exception)

        // Then
        assertEquals(result, InternalErrorException)

        verify(exactly = 2) { exception.code() }
        confirmVerified(exception)
    }

    @Test
    fun giveExceptionWhenGetHttpErrorThenReturnUnauthorized() {
        // Give
        every { exception.code() } answers { 401 }

        // When
        val result = httpErrors.getHttpError(exception)

        // Then
        assertEquals(result, Unauthorized)

        verify(exactly = 2) { exception.code() }
        confirmVerified(exception)
    }

    @Test
    fun giveExceptionWhenGetHttpErrorThenReturnHttpErrorCode() {
        // Give
        every { exception.code() } answers { 409 }

        // When
        val result = httpErrors.getHttpError(exception)

        // Then
        assertEquals(result, HttpErrorCode(409, "{}"))

        verify(exactly = 1) { exception.response() }
        verify(exactly = 2) { exception.code() }
        confirmVerified(exception)
    }
}
