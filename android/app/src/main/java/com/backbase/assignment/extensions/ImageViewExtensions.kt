package com.backbase.assignment.extensions

import android.widget.ImageView
import com.squareup.picasso.Picasso


fun ImageView.picassoLoad(url: String?) = Picasso.get().load(url).fit().into(this)