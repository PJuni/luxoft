package com.backbase.assignment.ui.movie

import androidx.recyclerview.widget.DiffUtil

class MovieImagesDiffCallback(
    private val oldData: List<String>,
    private val newData: List<String>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldData.size

    override fun getNewListSize() = newData.size

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ) = oldData[oldItemPosition] === newData[newItemPosition]

    override fun areContentsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ) = oldData[oldItemPosition] == newData[newItemPosition]
}
