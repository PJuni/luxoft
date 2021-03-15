package com.backbase.assignment.ui

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.backbase.assignment.BuildConfig
import com.backbase.assignment.R
import com.backbase.assignment.extensions.getIfContains
import com.backbase.assignment.extensions.picassoLoad
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import timber.log.Timber

class DialogMovieDetails : DialogFragment() {

    override fun onStart() {
        super.onStart()
        setWindowParams()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?) = Dialog(
        requireContext()
    ).apply {
        val imagePath = arguments?.getIfContains<String>(MOVIE_IMAGE)
        val name = arguments?.getIfContains<String>(MOVIE_NAME)
        val date = arguments?.getIfContains<String>(MOVIE_DATE)
        val desc = arguments?.getIfContains<String>(MOVIE_DESC)
        val genreNames = arguments?.getIfContains<ArrayList<String>>(MOVIES_GENRES) ?: emptyList()
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        Timber.i("_TEST ${BuildConfig.API_URL_IMAGES + imagePath}")
        Timber.i("_TEST $name")
        Timber.i("_TEST $date")
        Timber.i("_TEST $desc")
        Timber.i("_TEST $genreNames")

        with(layoutInflater.inflate(R.layout.dialog_movie_details, null)) {
            findViewById<ImageView>(R.id.imageMovieDetails).picassoLoad(BuildConfig.API_URL_IMAGES + imagePath)
            findViewById<TextView>(R.id.labelMovieDetailsName).text = name
            findViewById<TextView>(R.id.labelMovieDetailsDate).text = date
            findViewById<TextView>(R.id.labelMovieDetailsOverviewDesc).text = desc
            findViewById<ImageView>(R.id.imageMovieDetailsBack).setOnClickListener {
                dismiss()
            }

            genreNames.map { name ->
                Chip(context).apply {
                    text = name
                }
            }.forEach { chip ->
                findViewById<ChipGroup>(R.id.chipGroupMovieDetailsGenres).addView(chip)
            }

            setContentView(this)
        }
    }

    private fun setWindowParams() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )

    }

    companion object {
        private const val MOVIE_IMAGE = "MOVIE_IMAGE"
        private const val MOVIE_NAME = "MOVIE_NAME"
        private const val MOVIE_DATE = "MOVIE_DATE"
        private const val MOVIE_DESC = "MOVIE_DESC"
        private const val MOVIES_GENRES = "MOVIES_GENRES"

        fun newInstance(
            movieImage: String?,
            movieName: String?,
            movieDate: String?,
            movieDesc: String?,
            movieGenres: ArrayList<String>?,
        ): DialogMovieDetails = DialogMovieDetails().apply {
            arguments = Bundle().apply {
                putString(MOVIE_IMAGE, movieImage)
                putString(MOVIE_NAME, movieName)
                putString(MOVIE_DATE, movieDate)
                putString(MOVIE_DESC, movieDesc)
                putStringArrayList(MOVIES_GENRES, movieGenres)
            }
        }
    }
}