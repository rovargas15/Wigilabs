package com.example.domain.repository

import com.example.domain.exception.DomainException

interface DomainExceptionRepository {

    fun manageError(error: Throwable): DomainException
}
