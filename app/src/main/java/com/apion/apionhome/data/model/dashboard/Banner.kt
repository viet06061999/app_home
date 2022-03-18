package com.apion.apionhome.data.model.dashboard

import com.apion.apionhome.data.model.GeneraEntity
import com.apion.apionhome.data.model.House
import com.google.gson.annotations.SerializedName

data class Banner(
    @SerializedName("image")
    val image: String,
    @SerializedName("link")
    val link: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("created_at")
    val createdAt: String,
) : GeneraEntity {

    override fun areItemsTheSame(newItem: GeneraEntity): Boolean =
        newItem is Banner && this.image == newItem.image

    override fun areContentsTheSame(newItem: GeneraEntity): Boolean =
        newItem is Banner && this == newItem
}
