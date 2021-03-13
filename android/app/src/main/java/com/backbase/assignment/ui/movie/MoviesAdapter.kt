package com.backbase.assignment.ui.movie

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.backbase.assignment.R
import com.backbase.assignment.ui.custom.RatingView

class MoviesAdapter() :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    private val items: List<Any> = emptyList()

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

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var poster: ImageView
        lateinit var title: TextView
        lateinit var releaseDate: TextView
        lateinit var rating: RatingView

        fun bind(item: Any) = with(itemView) {
            poster = itemView.findViewById(R.id.poster)

            title = itemView.findViewById(R.id.title)
            title.text = ""

            releaseDate = itemView.findViewById(R.id.releaseDate)
            releaseDate.text = ""
        }
    }
}