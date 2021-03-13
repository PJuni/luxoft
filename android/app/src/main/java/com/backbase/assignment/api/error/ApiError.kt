package com.backbase.assignment.api.error

import java.io.IOException

data class NoInternetException(override val cause: Throwable? = null) : IOException()

sealed class ApiError : BaseApiException() {
    data class ServerError(
        override val originalException: Throwable? = null
    ) : ApiError()

    data class ServerUnavailable(
        override val originalException: Throwable? = null
    ) : ApiError()

    data class Unauthorized(
        override val originalException: Throwable? = null
    ) : ApiError()

    data class BadRequest(
        override val originalException: Throwable? = null
    ) : ApiError()

    data class NoInternetConnection(
        override val originalException: Throwable? = null
    ) : ApiError()

    data class SocketTimeout(
        override val originalException: Throwable? = null
    ) : ApiError()

    data class ProtocolException(
        override val originalException: Throwable? = null
    ) : ApiError()

    data class NoTokenFound(
        override val originalException: Throwable? = null
    ) : ApiError()

    data class JsonEncodingException(
        override val originalException: Throwable? = null
    ) : ApiError()

    data class JsonDataNotMatch(
        override val originalException: Throwable? = null
    ) : ApiError()

    data class Conflict(
        override val originalException: Throwable? = null
    ) : ApiError()

    data class Unknown(
        override val originalException: Throwable? = null
    ) : ApiError()

    data class EntityNotFound(
        override val originalException: Throwable? = null
    ) : ApiError()

    data class EndpointNotFound(
        override val originalException: Throwable? = null
    ) : ApiError()

    /**
     *  Special error case, when backend returns 204 when we awaited body
     */
    data class NoBody(
        override val originalException: Throwable? = null
    ) : ApiError()
}