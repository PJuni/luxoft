package com.backbase.assignment.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.backbase.assignment.api.response.doOnError
import com.backbase.assignment.api.response.doOnSuccess
import com.backbase.assignment.data.genres.GenreRepository
import com.backbase.assignment.data.movies.MoviesRepository
import com.backbase.assignment.extensions.empty
import com.backbase.assignment.model.Genre
import kotlinx.serialization.json.*
import timber.log.Timber

private const val FIRST_PAGE = 1

class MainViewModel(
    private val moviesRepository: MoviesRepository,
    private val genreRepository: GenreRepository,
) : ViewModel() {

    fun getMoviesPlayingNow(page: Int) = liveData {
        Timber.i("Fetching movies images.")
        moviesRepository
            .getPlayingNowMovies(page)
            .doOnSuccess { result ->
                val movieImages = result["results"]?.jsonArray?.mapNotNull {
                    it.jsonObject["poster_path"]?.jsonPrimitive?.content
                }
                Timber.i("Successfully fetched movies images: $movieImages")
                emit(movieImages ?: emptyList<JsonObject>())
            }
            .doOnError {
                Timber.e(it, "Failed to fetch movies.")
                emit(emptyList<JsonObject>())
            }
    }

    fun getMoviesMostPopular(page: Int = FIRST_PAGE) = liveData {
        Timber.i("Fetching most popular movies.")
        moviesRepository
            .getMostPopularMovies(page)
            .doOnSuccess { result ->
                Timber.i("Successfully fetched most popular movies: $result")
                val movies = result["results"]?.jsonArray?.toList()
                emit(movies ?: emptyList<JsonElement>())
            }
            .doOnError {
                Timber.e(it, "Failed to fetch most popular movies.")
                emit(emptyList<JsonElement>())
            }
    }

    fun getGenres() = liveData {
        Timber.i("Fetching genres.")
        genreRepository
            .getGenres()
            .doOnSuccess { result ->
                Timber.i("Successfully fetched genres: $result")
                val genres = result["genres"]?.jsonArray?.map {
                    Genre(
                        it.jsonObject["id"]?.jsonPrimitive?.int ?: 0,
                        it.jsonObject["name"]?.jsonPrimitive?.content ?: String.empty
                    )
                }
                emit(genres ?: emptyList<Genre>())
            }
            .doOnError {
                Timber.e(it, "Failed to fetch genres.")
                emit(emptyList<Genre>())
            }
    }

}