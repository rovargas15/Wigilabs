package com.example.domain.exception

open class DomainException(override val message: String = "") : Throwable(message)
object NotFoundException : DomainException()
object BadRequestException : DomainException()
object InternalErrorException : DomainException()
class UnknownError(override val message: String = "") : DomainException()
object Unauthorized : DomainException()
object TimeOutException : DomainException()
data class HttpErrorCode(val code: Int, override val message: String) : DomainException()
