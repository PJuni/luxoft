package com.backbase.assignment.ui.movie.playingNow

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.backbase.assignment.BuildConfig
import com.backbase.assignment.R
import com.backbase.assignment.extensions.picassoLoad
import com.backbase.assignment.extensions.setVisible


class MoviesPlayingNowAdapter : RecyclerView.Adapter<MoviesPlayingNowAdapter.ViewHolder>() {

    private var items: List<String> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.movie_item_playing_now,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(items[position])

    override fun getItemCount() = items.size

    fun setData(movieImages: List<String>?) {
        movieImages ?: return
        val diffResult = DiffUtil.calculateDiff(MoviesPlayingNowDiffCallback(items, movieImages))
        diffResult.dispatchUpdatesTo(this)
        items = movieImages
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image: ImageView = itemView.findViewById(R.id.imageMovieItemPlayingNow)
        private val progress: ProgressBar = itemView.findViewById(R.id.progressMovieItemPlayingNow)

        fun bind(imagePath: String) = with(itemView) {
            val imageFullPath = BuildConfig.API_URL_IMAGES + imagePath
            image.picassoLoad(
                imageFullPath,
                onSuccess = {
                    progress.setVisible(false)
                },
                onError = {
                    progress.setVisible(false)
                    image.setImageResource(R.drawable.vector_icon_error_24)
                }
            )
        }
    }
}