package com.backbase.assignment.data.movies

import com.backbase.assignment.api.response.ApiResult
import kotlinx.serialization.json.JsonObject

interface MoviesRepository {

    suspend fun getMostPopularMovies(page: Int): ApiResult<JsonObject>
    suspend fun getPlayingNowMovies(page: Int): ApiResult<JsonObject>
}