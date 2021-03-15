package com.backbase.assignment.extensions

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.backbase.assignment.R

enum class PaginationState {
    IDLE,
    LOADING,
}
private const val FIRST_PAGE = 1
// quick custom pagination
class RecyclerViewPagination(private val recyclerView: RecyclerView) {

    var paginationState = PaginationState.IDLE
    var currentPage = FIRST_PAGE

    fun addPagination(
        onNextPage: (Int) -> Unit
    ) = recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager =
                (recyclerView.layoutManager as? LinearLayoutManager) ?: return
            val visibleItemCount: Int = layoutManager.childCount
            val totalItemCount: Int = layoutManager.itemCount
            val firstVisibleItemPosition: Int = layoutManager.findFirstVisibleItemPosition()
            if (paginationState != PaginationState.LOADING
                && visibleItemCount + firstVisibleItemPosition >= totalItemCount
                && firstVisibleItemPosition >= 0
            ) {
                updateState(PaginationState.LOADING)

                onNextPage.invoke(++currentPage)
            }
        }
    })

    fun updateState(state: PaginationState) {
        paginationState = state
    }
}

fun RecyclerView.applyDivider(context: Context) {
    ContextCompat.getDrawable(context, R.drawable.view_separator)?.let {
        val divider = DividerItemDecoration(context, LinearLayoutManager.VERTICAL).apply {
            setDrawable(it)
        }
        addItemDecoration(divider)
    }
}