package com.backbase.assignment.extensions

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.backbase.assignment.R

// quick custom pagination
fun RecyclerView.addPagination(
    onNextPage: (Int) -> Unit
) = addOnScrollListener(object : RecyclerView.OnScrollListener() {
    private var page = 1
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val layoutManager =
            (layoutManager as? LinearLayoutManager) ?: return
        val visibleItemCount: Int = layoutManager.childCount
        val totalItemCount: Int = layoutManager.itemCount
        val firstVisibleItemPosition: Int = layoutManager.findFirstVisibleItemPosition()
        if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
            && firstVisibleItemPosition >= 0
        ) {
            page++
            onNextPage.invoke(page)
        }
    }
})

fun RecyclerView.applyDivider(context: Context) {
    ContextCompat.getDrawable(context, R.drawable.view_separator)?.let {
        val divider = DividerItemDecoration(context, LinearLayoutManager.VERTICAL).apply {
            setDrawable(it)
        }
        addItemDecoration(divider)
    }
}