package com.backbase.assignment.api.response

import com.backbase.assignment.api.error.ApiException
import retrofit2.Response

fun <A : Any> Response<A>.bodyOrException(): A {
    val body = body()
    return if (isSuccessful && body != null) body
    else throw ApiException(this)

}