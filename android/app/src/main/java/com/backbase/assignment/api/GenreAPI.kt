package com.backbase.assignment.api

import com.backbase.assignment.BuildConfig
import kotlinx.serialization.json.JsonObject
import retrofit2.Response
import retrofit2.http.GET

interface GenreAPI {

    @GET("genre/movie/list?language=en-US&api_key=${BuildConfig.API_KEY}")
    suspend fun getGenres(): Response<JsonObject>
}