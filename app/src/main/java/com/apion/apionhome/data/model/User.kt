package com.apion.apionhome.data.model

import android.os.Parcelable
import com.apion.apionhome.data.model.community.Participant
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("remember_token")
    val token: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("referal")
    val refer: String?,
    @SerializedName("dob")
    val dateOfBirth: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("avatar")
    val avatar: String,
    @SerializedName("sex")
    val sex: String,
    @SerializedName("academicLevel")
    val academicLevel: String,
    @SerializedName("job")
    val job: String,
    @SerializedName("pincode")
    val pincode: String,
    @SerializedName("isFirst")
    val isFirst: String,
    @SerializedName("position")
    val position: String,
    @SerializedName("permission")
    val permission: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("facebook_id")
    val facebook_id: String,
    @SerializedName("created_at")
    val created_at: String,
    @SerializedName("updated_at")
    val updated_at: String,
    @SerializedName("my_staff")
    val myStaff: List<User>?,
    @SerializedName("boss")
    val boss: User? = null,
    @SerializedName("house_sold")
    val houseSold: List<House>?,
    @SerializedName("my_houses")
    val myHouses: List<House>?,
    @SerializedName("participants")
    val participants: List<Participant>?,
    @SerializedName("houses_bookmark")
    val bookmarks: List<BookMark>?,
) : GeneraEntity, Parcelable {

    override fun areItemsTheSame(newItem: GeneraEntity): Boolean =
        newItem is User && this.id == newItem.id

    override fun areContentsTheSame(newItem: GeneraEntity): Boolean =
        newItem is User && this == newItem
}
