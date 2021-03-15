package com.backbase.assignment.data.movies

import com.backbase.assignment.api.MoviesAPI
import com.backbase.assignment.api.response.bodyOrException
import com.backbase.assignment.api.response.safeCall

class MoviesRepositoryImpl(
    private val moviesAPI: MoviesAPI
) : MoviesRepository {

    override suspend fun getMostPopularMovies(page: Int) = safeCall{
        moviesAPI.getPopularMovies(page).bodyOrException()
    }
    override suspend fun getPlayingNowMovies(page: Int) = safeCall{
        moviesAPI.getNowPlayingMovies(page).bodyOrException()
    }
}