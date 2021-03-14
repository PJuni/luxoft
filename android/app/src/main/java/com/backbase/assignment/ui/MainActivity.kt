package com.backbase.assignment.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.backbase.assignment.api.*
import com.backbase.assignment.databinding.ActivityMainBinding
import com.backbase.assignment.ui.movie.MoviesAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import kotlinx.serialization.json.jsonArray
import org.koin.android.ext.android.inject

class MainActivity() : AppCompatActivity() {

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
    }.doOnSuccess {
        moviesAdapter.setData(it["results"]?.jsonArray)
    }.doOnError {

    }
}
