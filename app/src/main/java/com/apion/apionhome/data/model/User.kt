package com.apion.apionhome.data.model

import android.os.Parcelable
import com.apion.apionhome.data.model.community.Participant
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class User (
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("phone")
    val phone: String? = null,

    @SerializedName("referal")
    val refer: String?= null,
    @SerializedName("dob")
    val dateOfBirth: String?= null,
    @SerializedName("address")
    val address: String? = null,
    @SerializedName("sex")
    val sex: String? = null,
    @SerializedName("academicLevel")
    val academicLevel: String? = null,
    @SerializedName("job")
    val job: String?= null,
    @SerializedName("pincode")
    val pincode: String? = null,
    @SerializedName("isFirst")
    val isFirst: String?= null,
    @SerializedName("position")
    val position: String?= null,
    @SerializedName("permission")
    val permission: String?= null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("facebook_id")
    val facebook_id: String?= null,
    @SerializedName("role")
    val role: String? = null,

    @SerializedName("remember_token")
    val rememberToken: String? = null,
    @SerializedName("avatar")
    val avatar: String? = null,
    @SerializedName("created_at")
    val created_at: String? = null,
    @SerializedName("updated_at")
    val updated_at: String? = null,
    @SerializedName("my_staff")
    val myStaff: List<User>? = null,
    @SerializedName("boss")
    val boss: User? = null,
    @SerializedName("house_sold")
    val houseSold: List<House>? = null,
    @SerializedName("my_houses")
    val myHouses: List<House>? = null,
    @SerializedName("participants")
    val participants: List<Participant>? = null,
    @SerializedName("houses_bookmark")
    val bookmarks: List<BookMark>?,
    @SerializedName("followed")
    val followed: List<UserFollowed>?,
    @SerializedName("following")
    val following: List<UserFollowing>?,
    @SerializedName("following_count")
    val followingCount: Int,
    @SerializedName("followed_count")
    val followedCount: Int,
    @SerializedName("my_houses_count")
    val myHousesCount: Int,
    @SerializedName("bios")
    val bios: String,
) : GeneraEntity, Parcelable {

    override fun areItemsTheSame(newItem: GeneraEntity): Boolean =
        newItem is User && this.id == newItem.id

    override fun areContentsTheSame(newItem: GeneraEntity): Boolean =
        newItem is User && this == newItem

    fun isFollowing(userId:String):Boolean{
        var isFollow = false
        following?.let {
            for (element in it) {
                if(element.beingFollowedId == userId){
                    isFollow = true
                    break
                }
            }
        }
        return isFollow
    }

    fun getPositionPermission():String{
        val textPer = if (permission == "Host Side") {
            "đầu chủ"
        } else {
            "đầu khách"
        }
        val textPos = when (position) {
            "Director" -> {
                "Giám đốc"
            }
            "Earl" -> {
                "Bá tước"
            }
            "Manager" -> {
                "Trưởng phòng"
            }
            "Leader" -> {
                "Trưởng nhóm"
            }
            "Captain" -> {
                "Đội trưởng"
            }
            "Expert" -> {
                "Chuyên viên"
            }
            else -> {
                "Đang bán"
            }
        }
        return "$textPos $textPer"
    }
}
