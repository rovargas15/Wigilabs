package com.example.domain.exception

import org.junit.Assert.assertEquals
import org.junit.Test
import java.net.ConnectException
import java.net.SocketTimeoutException

class CommonErrorsTest {

    private val commonErrors = CommonErrors()

    @Test
    fun giveThrowableWhenManageJavaErrorsThenReturnTimeOutException() {
        // Give
        val exception = SocketTimeoutException()

        // When
        val domainException = commonErrors.manageException(exception)

        // Then
        assertEquals(domainException, TimeOutException)
    }

    @Test
    fun giveThrowableWhenManageJavaErrorsThenReturnInternalErrorException() {
        // Give
        val exception = ConnectException()

        // When
        val domainException = commonErrors.manageException(exception)

        // Then
        assertEquals(domainException, InternalErrorException)
    }

    @Test
    fun giveThrowableWhenManageJavaErrorsThenReturnUnknownError() {
        // Give
        val exception = IllegalArgumentException()

        // When
        val domainException = commonErrors.manageException(exception)

        // Then
        assertEquals(domainException, UnknownError(String()))
    }
}
