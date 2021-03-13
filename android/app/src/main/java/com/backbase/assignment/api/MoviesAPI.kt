package com.backbase.assignment.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesAPI {

    @GET("/movie/now_playing?language=en-US")
    suspend fun getNowPlayingMovies(
        @Query(value = "apiKey") apiKey: String,
        @Query(value = "page") pageNumber: Int
    ): Response<Any>
}