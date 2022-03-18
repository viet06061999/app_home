package com.apion.apionhome.data.source.remote.response_entity

import com.apion.apionhome.data.model.User
import com.google.gson.annotations.SerializedName

data class AllUserResponse(

    @SerializedName("data")
    val users: List<User>,

    @SerializedName("success")
    val isSuccess: Boolean,

    @SerializedName("message")
    val message: String,
)
