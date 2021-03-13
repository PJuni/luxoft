package com.backbase.assignment.api.error

abstract class BaseApiException : Exception() {
    abstract val originalException: Throwable?
}