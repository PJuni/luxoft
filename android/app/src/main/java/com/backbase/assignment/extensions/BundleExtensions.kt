package com.backbase.assignment.extensions

import android.os.Bundle

inline fun <reified T> Bundle.getIfContains(key: String): T? = if (containsKey(key)) {
    get(key) as T?
} else null
