package com.backbase.assignment.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.backbase.assignment.api.*
import com.backbase.assignment.databinding.ActivityMainBinding
import com.backbase.assignment.ui.movie.mostPopular.MoviesMostPopularAdapter
import com.backbase.assignment.ui.movie.playingNow.MoviesPlayingNowAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.koin.android.ext.android.inject
import timber.log.Timber


class MainActivity : AppCompatActivity() {

    private var playingNowPage = 1
    private var mostPopularPage = 1

    private val moviesApi: MoviesAPI by inject()

    private val moviesPlayingNowAdapter = MoviesPlayingNowAdapter()
    private val moviesMostPopularAdapter = MoviesMostPopularAdapter()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerViewPlayingNow.adapter = moviesPlayingNowAdapter
        recyclerViewPlayingNow.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager =
                    (recyclerViewPlayingNow.layoutManager as? LinearLayoutManager) ?: return
                val visibleItemCount: Int = layoutManager.childCount
                val totalItemCount: Int = layoutManager.itemCount
                val firstVisibleItemPosition: Int = layoutManager.findFirstVisibleItemPosition()
                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                    && firstVisibleItemPosition >= 0
                ) {
                    playingNowPage++
                    getMoviesPlayingNow()
                }
            }
        })
        recyclerViewMostPopular.adapter = moviesMostPopularAdapter
        recyclerViewMostPopular.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager =
                    (recyclerViewMostPopular.layoutManager as? LinearLayoutManager) ?: return
                val visibleItemCount: Int = layoutManager.childCount
                val totalItemCount: Int = layoutManager.itemCount
                val firstVisibleItemPosition: Int = layoutManager.findFirstVisibleItemPosition()
                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                    && firstVisibleItemPosition >= 0
                ) {
                    mostPopularPage++
                    getMoviesMostPopular()
                }
            }
        })

        getMoviesPlayingNow()
        getMoviesMostPopular()


    }

    private fun getMoviesPlayingNow() = lifecycleScope.launch {
        safeCall {
            moviesApi.getNowPlayingMovies(playingNowPage).bodyOrException()
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

    private fun getMoviesMostPopular() = lifecycleScope.launch {
        safeCall {
            moviesApi.getPopularMovies(mostPopularPage).bodyOrException()
        }.doOnSuccess { result ->
            Timber.i("Successfully fetched most popular movies: $result")
            moviesMostPopularAdapter.setData(result["results"]?.jsonArray?.toList())
        }.doOnError {
            Timber.e(it, "Failed to fetch most popular movies.")
        }
    }
}
