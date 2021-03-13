package com.backbase.assignment.api.error

import com.backbase.assignment.api.error.ApiError.*
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection
import java.net.ProtocolException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun Throwable.toApiError(): ApiError = when (this) {
    is ApiError -> this
    is ApiException -> toApiError()
    is IOException -> toApiError()
    is RuntimeException -> toApiError()
    else -> Unknown(originalException = this)
}

internal fun IOException.toApiError(): ApiError = when (this) {
    is UnknownHostException -> ServerUnavailable(originalException = this)
    is NoInternetException -> NoInternetConnection(originalException = this)
    is SocketTimeoutException -> SocketTimeout(originalException = this)
    is ProtocolException -> ProtocolException(originalException = this)
    else -> Unknown(originalException = this)
}

internal fun RuntimeException.toApiError(): ApiError = when (this) {
    is NullPointerException -> toApiError()
    is HttpException -> toApiError()
    else -> Unknown(originalException = this)
}

internal fun HttpException.toApiError() = when {
    code() == HttpURLConnection.HTTP_UNAUTHORIZED -> Unauthorized(originalException = cause)
    code() == HttpURLConnection.HTTP_BAD_REQUEST -> BadRequest(originalException = cause)
    else -> Unknown(originalException = cause)
}

internal fun NullPointerException.toApiError(): ApiError = if (message.isNullOrEmpty().not())
    EntityNotFound()
else Unknown(originalException = this)

internal fun ApiException.toApiError(): ApiError = when {
    this.code == HttpURLConnection.HTTP_FORBIDDEN -> NoTokenFound(originalException = this)
    this.code == HttpURLConnection.HTTP_NO_CONTENT -> NoBody(originalException = this)
    this.code == HttpURLConnection.HTTP_CONFLICT -> Conflict(originalException = this)
    this.code >= HttpURLConnection.HTTP_INTERNAL_ERROR -> ServerError(originalException = this)
    this.code == HttpURLConnection.HTTP_BAD_REQUEST -> BadRequest(originalException = this)
    this.code == HttpURLConnection.HTTP_NOT_FOUND -> EndpointNotFound(originalException = this)
    else -> ServerError(originalException = this)
}
