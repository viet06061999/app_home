package com.apion.apionhome.data.source.remote.response_entity

import com.apion.apionhome.data.model.User
import com.apion.apionhome.data.model.community.Community
import com.google.gson.annotations.SerializedName

data class CommunityResponse(
    @SerializedName("success")
    val isSuccess: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val community: Community
)
