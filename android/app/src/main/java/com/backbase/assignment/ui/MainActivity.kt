package com.backbase.assignment.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.backbase.assignment.R
import com.backbase.assignment.databinding.ActivityMainBinding
import com.backbase.assignment.ui.movie.MoviesAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URL

class MainActivity : AppCompatActivity() {
    
    private val yourKey = ""

    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        moviesAdapter = MoviesAdapter()
        recyclerView.adapter = moviesAdapter

        fetchMovies()
    }

    private fun fetchMovies() {
        val jsonString =
            URL("/movie/now_playing?language=en-US&page=undefined&api_key=$yourKey").readText()
        val jsonObject = JsonParser.parseString(jsonString).asJsonObject
        moviesAdapter.items = jsonObject["results"] as JsonArray
        moviesAdapter.notifyDataSetChanged()
    }
}
