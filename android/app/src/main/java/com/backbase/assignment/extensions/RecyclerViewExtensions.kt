package com.backbase.assignment.extensions

import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.InsetDrawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.backbase.assignment.R

fun RecyclerView.applyDivider(context: Context) {
    ContextCompat.getDrawable(context, R.drawable.view_separator)?.let {
        val insetDivider = createInsetDivider(it, createInsetRect(context))

        val divider = DividerItemDecoration(context, LinearLayoutManager.VERTICAL).apply {
            setDrawable(it)
        }
        addItemDecoration(divider)
    }
}

private fun createInsetRect(context: Context) = Rect().apply {
    val distance = context.resources.getDimensionPixelSize(R.dimen.margin_16)
    top = distance
}

private fun createInsetDivider(it: Drawable, rect: Rect) = InsetDrawable(
    it,
    rect.left,
    rect.top,
    rect.right,
    rect.bottom
).apply { isAutoMirrored = true }