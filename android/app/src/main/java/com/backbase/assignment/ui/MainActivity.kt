package com.backbase.assignment.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.backbase.assignment.api.GenreAPI
import com.backbase.assignment.api.MoviesAPI
import com.backbase.assignment.api.response.bodyOrException
import com.backbase.assignment.api.response.doOnError
import com.backbase.assignment.api.response.doOnSuccess
import com.backbase.assignment.api.response.safeCall
import com.backbase.assignment.databinding.ActivityMainBinding
import com.backbase.assignment.extensions.addPagination
import com.backbase.assignment.extensions.applyDivider
import com.backbase.assignment.extensions.empty
import com.backbase.assignment.model.Genre
import com.backbase.assignment.ui.custom.DialogMovieDetails
import com.backbase.assignment.ui.movie.mostPopular.MoviesMostPopularAdapter
import com.backbase.assignment.ui.movie.playingNow.MoviesPlayingNowAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.koin.android.ext.android.inject
import timber.log.Timber

private const val FIRST_PAGE = 1

class MainActivity : AppCompatActivity() {

    private val moviesApi: MoviesAPI by inject()
    private val genreApi: GenreAPI by inject()
    private var genres = emptyList<Genre>()

    private val moviesPlayingNowAdapter = MoviesPlayingNowAdapter()
    private val moviesMostPopularAdapter = MoviesMostPopularAdapter { item ->
        val itemGenres = item.jsonObject["genre_ids"]?.jsonArray?.map {
            it.jsonPrimitive.int
        } ?: emptyList()

        val genreNames = genres.filter { genre ->
            itemGenres.contains(genre.id)
        }.map {
            it.name
        }
        DialogMovieDetails.newInstance(
            item.jsonObject["poster_path"]?.jsonPrimitive?.content,
            item.jsonObject["title"]?.jsonPrimitive?.content,
            item.jsonObject["release_date"]?.jsonPrimitive?.content,
            item.jsonObject["overview"]?.jsonPrimitive?.content,
            ArrayList(genreNames)
        ).show(supportFragmentManager, javaClass.name)
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(recyclerViewPlayingNow) {
            adapter = moviesPlayingNowAdapter
            addPagination { page ->
                getMoviesPlayingNow(page)
            }
        }

        with(recyclerViewMostPopular) {
            adapter = moviesMostPopularAdapter
            addPagination { page ->
                getMoviesMostPopular(page)
            }
            applyDivider(this@MainActivity)
        }

        getMoviesPlayingNow()
        getMoviesMostPopular()
        getGenres()
    }

    private fun getMoviesPlayingNow(page: Int = FIRST_PAGE) = lifecycleScope.launch {
        safeCall {
            Timber.i("Fetching movies images.")
            moviesApi.getNowPlayingMovies(page).bodyOrException()
        }.doOnSuccess { result ->
            val movieImages = result["results"]?.jsonArray?.mapNotNull {
                it.jsonObject["poster_path"]?.jsonPrimitive?.content
            }
            Timber.i("Successfully fetched movies images: $movieImages")
            moviesPlayingNowAdapter.setData(movieImages)
        }.doOnError {
            Timber.e(it, "Failed to fetch movies.")
        }
    }

    private fun getMoviesMostPopular(page: Int = FIRST_PAGE) = lifecycleScope.launch {
        safeCall {
            Timber.i("Fetching most popular movies.")
            moviesApi.getPopularMovies(page).bodyOrException()
        }.doOnSuccess { result ->
            Timber.i("Successfully fetched most popular movies: $result")
            moviesMostPopularAdapter.setData(result["results"]?.jsonArray?.toList())
        }.doOnError {
            Timber.e(it, "Failed to fetch most popular movies.")
        }
    }

    private fun getGenres() = lifecycleScope.launch {
        safeCall {
            Timber.i("Fetching genres.")
            genreApi.getGenres().bodyOrException()
        }.doOnSuccess { result ->
            Timber.i("Successfully fetched genres: $result")
            genres = result["genres"]?.jsonArray?.map {
                Genre(
                    it.jsonObject["id"]?.jsonPrimitive?.int ?: 0,
                    it.jsonObject["name"]?.jsonPrimitive?.content ?: String.empty
                )
            } ?: emptyList()
        }.doOnError {
            Timber.e(it, "Failed to fetch genres.")
        }
    }
}
