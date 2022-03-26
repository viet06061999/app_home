package com.apion.apionhome.data.model

import android.os.Parcelable
import android.text.format.DateUtils
import com.apion.apionhome.utils.TimeFormat
import com.apion.apionhome.utils.toString
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.*


@Parcelize
data class House(
    @SerializedName("id")
    val id: Int,
    @SerializedName("news_type")
    val newsType: String?,
    @SerializedName("houseType")
    val houseType: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("province")
    val province: String?,
    @SerializedName("district")
    val district: String?,
    @SerializedName("ward")
    val ward: String?,
    @SerializedName("street")
    val street: String?,
    @SerializedName("detail_address")
    val address: String?,
    @SerializedName("price")
    val price: Long?,
    @SerializedName("frontWidth")
    val frontWidth: Double?,
    @SerializedName("acreage")
    val acreage: Double?,
    @SerializedName("homeDirection")
    val homeDirection: String?,
    @SerializedName("bedrooms")
    val bedrooms: Int?,
    @SerializedName("user_id")
    val userId: Int?,
    @SerializedName("seller_id")
    val sellerId: Int?,
    @SerializedName("owner")
    val owner: User?,
    @SerializedName("seller")
    val seller: User?,
    @SerializedName("images")
    var images: List<String>?,
    @SerializedName("related_houses")
    val relatedHouses: List<House>?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("updated_at")
    val updatedAt: String?,
) : GeneraEntity, Parcelable {

    override fun areItemsTheSame(newItem: GeneraEntity): Boolean =
        newItem is House && this.id == newItem.id

    override fun areContentsTheSame(newItem: GeneraEntity): Boolean =
        newItem is House && this == newItem

    fun getPriceConvert(): String {
        val tmpPrice = price ?: 0
        return when {
            tmpPrice / 1000000000.0 >= 1.0 -> {
                val value = tmpPrice / 1000000000.0
                return "${checkPrice(value)} tỷ"
            }
            tmpPrice / 1000000.0 >= 1.0 -> {
                val value = tmpPrice / 1000000.0
                return "${checkPrice(value)} triệu"
            }
            tmpPrice / 1000.0 >= 1.0 -> {
                val value = tmpPrice / 1000.0
                return "${checkPrice(value)} nghìn"
            }
            else -> "$price đồng"
        }
    }

    fun getDefaultImage(): String? {
        return if (!images.isNullOrEmpty()) images?.get(0)
        else null
    }

    fun getDetailAddress(): String {
        return "$address $street, $ward, $district, $province"
    }

    fun getCreateDate(): String {
        val date = SimpleDateFormat(
            TimeFormat.TIME_FORMAT_API,
            Locale.getDefault()
        ).parse(createdAt)
        return date?.let {
            date.toString(TimeFormat.DATE_TIME_FORMAT_APP_FULL)
        } ?: ""
    }

    fun getDateAgo(): String {
        val date = SimpleDateFormat(
            TimeFormat.TIME_FORMAT_API,
            Locale.getDefault()
        ).parse(updatedAt)
        return DateUtils.getRelativeTimeSpanString(
            date?.time ?: Calendar.getInstance().timeInMillis,
            Calendar.getInstance().timeInMillis,
            DateUtils.MINUTE_IN_MILLIS
        ).toString()
    }

    private fun checkPrice(number: Double): String {
        val priceString = String.format("%.2f", number)
        return if (priceString.split(".").last() == "00") priceString.split(".")
            .first() else priceString

    }
}
