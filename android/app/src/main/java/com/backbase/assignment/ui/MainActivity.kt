package com.backbase.assignment.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.backbase.assignment.databinding.ActivityMainBinding
import com.backbase.assignment.extensions.PaginationState
import com.backbase.assignment.extensions.RecyclerViewPagination
import com.backbase.assignment.extensions.applyDivider
import com.backbase.assignment.extensions.reObserve
import com.backbase.assignment.model.Genre
import com.backbase.assignment.ui.custom.DialogMovieDetails
import com.backbase.assignment.ui.movie.mostPopular.MoviesMostPopularAdapter
import com.backbase.assignment.ui.movie.playingNow.MoviesPlayingNowAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.serialization.json.*
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()

    private var genres = emptyList<Genre>()

    private val recyclerMoviesPopularPagination by lazy {
        RecyclerViewPagination(recyclerViewMostPopular)
    }

    private val recyclerMoviesPlayingNowPagination by lazy {
        RecyclerViewPagination(recyclerViewPlayingNow)
    }

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

    private val moviesPlayingNowObserver: Observer<List<String>> by lazy {
        Observer<List<String>> { movieImages ->
            moviesPlayingNowAdapter.setData(movieImages)
            recyclerMoviesPlayingNowPagination.updateState(PaginationState.IDLE)
        }
    }

    private val moviesMostPopularObserver: Observer<List<JsonElement>> by lazy {
        Observer<List<JsonElement>> { items ->
            moviesMostPopularAdapter.setData(items)
            recyclerMoviesPopularPagination.updateState(PaginationState.IDLE)
        }
    }

    private val genresObserver: Observer<List<Genre>> by lazy {
        Observer<List<Genre>> { genres ->
            this.genres = genres
        }
    }

    private lateinit var binding: ActivityMainBinding
    fun <T> MutableLiveData<T>.forceRefresh() {
        this.value = this.value
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initObservers()

        with(recyclerViewPlayingNow) {
            adapter = moviesPlayingNowAdapter
            recyclerMoviesPlayingNowPagination.addPagination { page ->
                mainViewModel.getMoviesPlayingNow(page).reObserve(this@MainActivity, moviesPlayingNowObserver)
            }
        }

        with(recyclerViewMostPopular) {
            adapter = moviesMostPopularAdapter
            recyclerMoviesPopularPagination.addPagination { page ->
                mainViewModel.getMoviesMostPopular(page).reObserve(this@MainActivity, moviesMostPopularObserver)
            }
            applyDivider(this@MainActivity)
        }
    }

    private fun initObservers() {
        mainViewModel.getMoviesPlayingNow().reObserve(this, moviesPlayingNowObserver)
        mainViewModel.getMoviesMostPopular().reObserve(this, moviesMostPopularObserver)
        mainViewModel.getGenres().reObserve(this, genresObserver)
    }
}
