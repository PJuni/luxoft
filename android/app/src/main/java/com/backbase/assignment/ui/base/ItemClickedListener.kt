package com.backbase.assignment.ui.base

fun interface ItemClickedListener<T> {
    fun itemClicked(item: T)
}