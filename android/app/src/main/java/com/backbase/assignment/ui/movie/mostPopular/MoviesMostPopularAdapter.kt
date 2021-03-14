package com.backbase.assignment.ui.movie.mostPopular

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.backbase.assignment.BuildConfig
import com.backbase.assignment.R
import com.backbase.assignment.extensions.picassoLoad
import com.backbase.assignment.extensions.setVisible
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlin.math.roundToInt


class MoviesMostPopularAdapter : RecyclerView.Adapter<MoviesMostPopularAdapter.ViewHolder>() {

    private val items = mutableListOf<JsonElement>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.movie_item,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(items[position])

    override fun getItemCount() = items.size

    fun setData(movies: List<JsonElement>?) {
        movies ?: return
        val diffResult = DiffUtil.calculateDiff(MoviesMostPopularDiffCallback(items, movies))
        diffResult.dispatchUpdatesTo(this)
        items.addAll(movies)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image: ImageView = itemView.findViewById(R.id.poster)
        private val title: TextView = itemView.findViewById(R.id.title)
        private val releaseDate: TextView = itemView.findViewById(R.id.releaseDate)
        private val rating: TextView = itemView.findViewById(R.id.rating)
        private val progress: ProgressBar = itemView.findViewById(R.id.progress)

        fun bind(item: JsonElement) = with(itemView) {
            val imageFullPath = BuildConfig.API_URL_IMAGES + item.jsonObject["poster_path"]?.jsonPrimitive?.content
            image.picassoLoad(
                imageFullPath,
                onSuccess = {
                    progress.setVisible(false)
                    title.text = item.jsonObject["original_title"]?.jsonPrimitive?.content
                    releaseDate.text = item.jsonObject["release_date"]?.jsonPrimitive?.content
                    rating.text = (item.jsonObject["popularity"].toString().toDouble().roundToInt() / 100).toString()
                },
                onError = {
                    progress.setVisible(false)
                    image.setImageResource(R.drawable.vector_icon_error_24)
                }
            )
        }
    }
}