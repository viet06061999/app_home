package com.apion.apionhome.data.model

import android.os.Parcelable
import com.apion.apionhome.utils.*
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

@Parcelize
data class House(
    @SerializedName("id")
    val id: Int,
    @SerializedName("news_type")
    val newsType: String,
    @SerializedName("houseType")
    val houseType: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("province")
    val province: String,
    @SerializedName("district")
    val district: String,
    @SerializedName("ward")
    val ward: String,
    @SerializedName("street")
    val street: String,
    @SerializedName("detail_address")
    val address: String,
    @SerializedName("price")
    val price: Long,
    @SerializedName("frontWidth")
    val frontWidth: Double,
    @SerializedName("acreage")
    val acreage: Double,
    @SerializedName("homeDirection")
    val homeDirection: String,
    @SerializedName("bedrooms")
    val bedrooms: Int,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("seller_id")
    val sellerId: Int?,
    @SerializedName("owner")
    val owner: User?,
    @SerializedName("seller")
    val seller: User?,
    @SerializedName("images")
    var images: List<String>,
    @SerializedName("related_houses")
    val relatedHouses: List<House>?,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
) : GeneraEntity, Parcelable {

    override fun areItemsTheSame(newItem: GeneraEntity): Boolean =
        newItem is House && this.id == newItem.id

    override fun areContentsTheSame(newItem: GeneraEntity): Boolean =
        newItem is House && this == newItem

    fun getPriceConvert(): String {
        return when {
            price / 1000000000.0 >= 1.0 -> {
                val value = price / 1000000000.0
                if (checkPrice(value)) return "${value.roundToInt()} tỷ"
                else return "$value tỷ"
            }
            price / 1000000.0 >= 1.0 -> {
                val value = price / 1000000.0
                if (checkPrice(value)) return "${value.roundToInt()} triệu"
                else return "$value triệu"
            }
            price / 1000.0 >= 1.0 -> {
                val value = price / 1000.0
                if (checkPrice(value)) return "${value.roundToInt()} nghìn"
                else return "$value nghìn"
            }
            else -> "$price đồng"
        }
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

    private fun checkPrice(number: Double): Boolean {
        return number.roundToInt() - number == 0.0
    }
}
