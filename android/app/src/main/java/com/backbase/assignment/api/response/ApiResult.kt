package com.backbase.assignment.api.response

import com.backbase.assignment.api.error.ApiError
import com.backbase.assignment.api.error.toApiError

sealed class ApiResult<out T> {

    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Failure(val error: ApiError) : ApiResult<Nothing>()
    object Any : ApiResult<Nothing>()
}

inline infix fun <T> ApiResult<T>.mapError(apply: (ApiError) -> ApiError): ApiResult<T> =
    when (this) {
        is ApiResult.Failure -> ApiResult.Failure(apply(error))
        is ApiResult.Success -> this
        is ApiResult.Any -> this
    }

inline infix fun <T, R> ApiResult<T>.map(apply: (T) -> R): ApiResult<R> = when (this) {
    is ApiResult.Failure -> this
    is ApiResult.Success -> ApiResult.Success(apply(data))
    is ApiResult.Any -> this
}

inline infix fun <T, R> ApiResult<T>.flatMap(apply: (T) -> ApiResult<R>): ApiResult<R> =
    when (this) {
        is ApiResult.Failure -> this
        is ApiResult.Success -> apply(data)
        is ApiResult.Any -> this
    }

inline infix fun <T> ApiResult<T>.doOnSuccess(apply: (T) -> Unit): ApiResult<T> {
    if (this is ApiResult.Success) {
        apply(data)
    }
    return this
}

inline infix fun <T> ApiResult<T>.doOnError(apply: (ApiError) -> Unit): ApiResult<T> {
    if (this is ApiResult.Failure) {
        apply(error)
    }
    return this
}

inline infix fun <T> ApiResult<T>.doOnFinal(apply: () -> Unit): ApiResult<T> {
    apply()
    return this
}

infix fun <T, R> ApiResult<(T) -> R>.apply(apply: ApiResult<T>): ApiResult<R> = when (this) {
    is ApiResult.Failure -> this
    is ApiResult.Success -> apply.map(this.data)
    is ApiResult.Any -> this
}

inline fun <T, reified A> ApiResult<T>.fold(
    error: (ApiError) -> A = { this as A },
    success: (T) -> A = { this as A },
    any: () -> A = { this as A }
): A = when (this) {
    is ApiResult.Failure -> error(this.error)
    is ApiResult.Success -> success(this.data)
    is ApiResult.Any -> any()
}

fun <T> success(data: T) = ApiResult.Success(data)

fun failure(apiError: ApiError) = ApiResult.Failure(apiError)

inline fun <T> safeCall(call: () -> T): ApiResult<T> = try {
    ApiResult.Success(call.invoke())
} catch (exception: Exception) {
    val apiException = exception.toApiError()

    ApiResult.Failure(apiException)
}