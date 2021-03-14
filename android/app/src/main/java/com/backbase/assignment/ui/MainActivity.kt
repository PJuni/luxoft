package com.backbase.assignment.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.backbase.assignment.api.*
import com.backbase.assignment.databinding.ActivityMainBinding
import com.backbase.assignment.extensions.toUriPath
import com.backbase.assignment.ui.movie.MoviesAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import org.koin.android.ext.android.inject
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val yourKey = ""

    private val moviesApi: MoviesAPI by inject()

    private val moviesAdapter: MoviesAdapter = MoviesAdapter()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView.adapter = moviesAdapter

        lifecycleScope.launch {
            fetchMovies()
        }

    }

    private suspend fun fetchMovies() = safeCall {
        moviesApi.getNowPlayingMovies(2).bodyOrException()
    }.doOnSuccess { result ->
        val movieImages = result["results"]?.jsonArray?.map {
            it.jsonObject["poster_path"].toString().toUriPath()
        }
        Timber.i("Successfully fetched movies images: $movieImages")
        moviesAdapter.setData(movieImages)
    }.doOnError {
        Timber.e(it, "Failed to fetch movies.")
    }
}
