package com.backbase.assignment.ui.movie

import androidx.recyclerview.widget.DiffUtil
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.jsonObject

class MovieDiffCallback(
    private val oldData: JsonArray,
    private val newData: JsonArray
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldData.size

    override fun getNewListSize() = newData.size

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ) = oldData[oldItemPosition].jsonObject["id"] === newData[newItemPosition].jsonObject["id"]

    override fun areContentsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ) = oldData[oldItemPosition] == newData[newItemPosition]
}
