package com.backbase.assignment.data.genres

import com.backbase.assignment.api.GenreAPI
import com.backbase.assignment.api.response.bodyOrException
import com.backbase.assignment.api.response.safeCall

class GenreRepositoryImpl(
    private val genreAPI: GenreAPI
) : GenreRepository {

    override suspend fun getGenres() = safeCall {
        genreAPI.getGenres().bodyOrException()
    }
}