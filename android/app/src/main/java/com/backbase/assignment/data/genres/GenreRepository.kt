package com.backbase.assignment.data.genres

import com.backbase.assignment.api.response.ApiResult
import kotlinx.serialization.json.JsonObject

interface GenreRepository {

    suspend fun getGenres(): ApiResult<JsonObject>
}