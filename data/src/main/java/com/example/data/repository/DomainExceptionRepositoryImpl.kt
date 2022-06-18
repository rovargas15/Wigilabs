package com.example.data.repository

import com.example.data.exception.HttpErrors
import com.example.domain.exception.CommonErrors
import com.example.domain.exception.DomainException
import com.example.domain.repository.DomainExceptionRepository
import retrofit2.HttpException
import timber.log.Timber

class DomainExceptionRepositoryImpl(
    private val commonErrors: CommonErrors,
    private val httpErrors: HttpErrors
) : DomainExceptionRepository {

    override fun manageError(error: Throwable): DomainException {
        Timber.e(error)
        return if (error is HttpException) {
            httpErrors.getHttpError(error)
        } else {
            commonErrors.manageException(error)
        }
    }
}
