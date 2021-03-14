package com.backbase.assignment.extensions

import android.widget.ImageView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception


fun ImageView.picassoLoad(
    url: String?,
    onError: () -> Unit = {},
    onSuccess: () -> Unit = {},
) = Picasso.get().load(url).fit().into(this, object :Callback {
    override fun onSuccess() {
        onSuccess.invoke()
    }

    override fun onError(e: Exception?) {
        onError.invoke()
    }
})