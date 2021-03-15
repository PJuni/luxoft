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
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val moviesApi: MoviesAPI by inject()
    private val genreApi: GenreAPI by inject()
    val mainViewModel: MainViewModel by viewModel()

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
            }
        }

        with(recyclerViewMostPopular) {
            adapter = moviesMostPopularAdapter
            addPagination { page ->
            }
            applyDivider(this@MainActivity)
        }
    }


}
