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
    val id: Int = -1,
    @SerializedName("news_type")
    val newsType: String? = "For Sale",
    @SerializedName("houseType")
    val houseType: String?,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("title")
    val title: String?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("province")
    val province: String?,
    @SerializedName("district")
    val district: String?,
    @SerializedName("ward")
    val ward: String? = null,
    @SerializedName("street")
    val street: String? = null,
    @SerializedName("detail_address")
    val address: String? = null,
    @SerializedName("price")
    val price: Long? = 0,
    @SerializedName("frontWidth")
    val frontWidth: Double?,
    @SerializedName("acreage")
    val acreage: Double? = 0.0,
    @SerializedName("homeDirection")
    val homeDirection: String?,
    @SerializedName("bedrooms")
    val bedrooms: Int? = 0,
    @SerializedName("user_id")
    val userId: Int?,
    @SerializedName("seller_id")
    val sellerId: Int? = null,
    @SerializedName("owner")
    val owner: User? = null,
    @SerializedName("seller")
    val seller: User? = null,
    @SerializedName("images")
    var images: List<String>? = null,
    @SerializedName("related_houses")
    val relatedHouses: List<House>? = null,
    @SerializedName("created_at")
    val createdAt: String? = null,
    @SerializedName("updated_at")
    val updatedAt: String? = null,
    @SerializedName("commission_rate")
    val commissionRate: String? = "3",
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

    fun getCommissionConvert(): String {
        var tmpPrice = (price ?: 0) * (commissionRate?.toInt() ?: 3) / 100
        if (status == "In Review") tmpPrice = 0
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
            else -> "$tmpPrice đồng"
        }
    }

    fun getDefaultImage(): String? {
        return if (!images.isNullOrEmpty()) images?.get(0)
        else null
    }

    fun getDetailAddress(): String {
        var textAddress = ""
        address.let {
            if (address?.isNotEmpty() == true) {
                textAddress += address
                textAddress += ", "
            }
        }
        street.let {
            if (street?.isNotEmpty() == true) {
                textAddress += street
                textAddress += ", "
            }
        }

        ward.let {
            if (ward?.isNotEmpty() == true) {
                textAddress += ward
                textAddress += ", "
            }
        }
        district.let {
            if (district?.isNotEmpty() == true) {
                textAddress += district
                textAddress += ", "
            }
        }

        province.let {
            textAddress += province
        }

        return textAddress
    }

    fun getCreateDate(): String {
        val date = SimpleDateFormat(
            TimeFormat.TIME_FORMAT_API,
            Locale.getDefault()
        ).parse(createdAt)
        return date?.let {
            date.toString(TimeFormat.DATE_FORMAT)
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

    fun getRooms(): Int {
        return bedrooms ?: 0
    }

    private fun checkPrice(number: Double): String {
        val priceString = String.format("%.2f", number)
        return if (priceString.split(".").last() == "00") priceString.split(".")
            .first() else priceString

    }
}
