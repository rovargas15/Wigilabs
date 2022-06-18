package com.example.domain.exception

import java.net.ConnectException
import java.net.SocketTimeoutException

class CommonErrors {

    fun manageException(throwable: Throwable): DomainException {
        return manageJavaErrors(throwable)
    }

    private fun manageJavaErrors(throwable: Throwable): DomainException {
        return when (throwable) {
            is SocketTimeoutException -> TimeOutException
            is ConnectException -> InternalErrorException
            else -> UnknownError(message = throwable.message ?: String())
        }
    }
}
