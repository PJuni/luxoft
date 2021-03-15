package com.backbase.assignment.di

import com.backbase.assignment.BuildConfig
import com.backbase.assignment.api.GenreAPI
import com.backbase.assignment.api.MoviesAPI
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import timber.log.Timber

val contentType = "application/json".toMediaType()

@ExperimentalSerializationApi
val retrofitModule = module {
    single { okHttp() }
    single { retrofit() }
    single { get<Retrofit>().create(MoviesAPI::class.java) }
    single { get<Retrofit>().create(GenreAPI::class.java) }
}

private fun okHttpLogger() = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger() {
    Timber.i(it)
})

private fun okHttp() = OkHttpClient.Builder()
    .addInterceptor(okHttpLogger())
    .build()

@ExperimentalSerializationApi
private fun retrofit() = Retrofit.Builder()
    .callFactory(OkHttpClient.Builder().build())
    .baseUrl(BuildConfig.API_URL)
    .addConverterFactory(Json.asConverterFactory(contentType))
    .build()