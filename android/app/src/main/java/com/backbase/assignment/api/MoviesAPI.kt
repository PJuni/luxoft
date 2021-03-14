package com.backbase.assignment.api

import com.backbase.assignment.BuildConfig
import kotlinx.serialization.json.JsonObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesAPI {

    @GET("movie/now_playing?language=en-US&api_key=${BuildConfig.API_KEY}")
    suspend fun getNowPlayingMovies(
        @Query(value = "page") pageNumber: Int
    ): Response<JsonObject>
}