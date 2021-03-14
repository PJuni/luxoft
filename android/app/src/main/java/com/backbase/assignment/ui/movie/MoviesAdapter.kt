package com.backbase.assignment.ui.movie

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.backbase.assignment.R
import com.backbase.assignment.extensions.picassoLoad
import com.backbase.assignment.extensions.toUriPath
import com.backbase.assignment.ui.custom.RatingView
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import timber.log.Timber
private val EMPTY_MOVIES = JsonArray(emptyList())
class MoviesAdapter() :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    private var items: JsonArray = EMPTY_MOVIES

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.movie_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(items[position])

    override fun getItemCount() = items.size

    fun setData(movies: JsonArray?) {
        movies ?: return
        val diffResult = DiffUtil.calculateDiff(MovieDiffCallback(items, movies))
        diffResult.dispatchUpdatesTo(this)
        items = movies
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var poster: ImageView
        lateinit var title: TextView
        lateinit var releaseDate: TextView
        lateinit var rating: RatingView

        fun bind(item: JsonElement) = with(itemView) {
            Timber.i("_TEST, $item")
            poster = itemView.findViewById(R.id.poster)
            val imagePath = item.jsonObject["poster_path"].toString().toUriPath()
            poster.picassoLoad("https://image.tmdb.org/t/p/original$imagePath")

            title = itemView.findViewById(R.id.title)
            title.text = item.jsonObject["title"].toString()

            releaseDate = itemView.findViewById(R.id.releaseDate)
            releaseDate.text = item.jsonObject["release_date"].toString()
        }
    }
}