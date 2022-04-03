package com.apion.apionhome.data.model

import android.os.Parcelable
import com.apion.apionhome.data.model.community.Participant
import com.apion.apionhome.utils.TimeFormat
import com.apion.apionhome.utils.toString
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

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
    var email: String? = null,
    @SerializedName("facebook_id")
    var facebook_id: String?= null,
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
    val bookmarks: List<BookMark>? = null,
    @SerializedName("followed")
    val followed: List<UserFollowed>? = null,
    @SerializedName("following")
    val following: List<UserFollowing>? = null,
    @SerializedName("following_count")
    val followingCount: Int? = null,
    @SerializedName("followed_count")
    val followedCount: Int? = null,
    @SerializedName("my_houses_count")
    val myHousesCount: Int? = null,
    @SerializedName("bios")
    val bios: String? = null,
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

    fun getDOBDate(): String {
        val date = SimpleDateFormat(
            TimeFormat.TIME_FORMAT_API_1,
            Locale.getDefault()
        ).parse(dateOfBirth)
        return date?.let {
            date.toString(TimeFormat.DATE_FORMAT)
        } ?: ""
    }

    fun getSexString(): String {
        return if(sex == "Male") "Nam" else "Nữ"
    }
}
