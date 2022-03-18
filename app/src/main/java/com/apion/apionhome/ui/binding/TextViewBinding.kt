package com.apion.apionhome.ui.binding

import android.graphics.Color
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.apion.apionhome.MyApplication
import com.apion.apionhome.R
import com.apion.apionhome.data.model.community.Community
import java.util.*

@BindingAdapter("type")
fun houseType(view: TextView, type: String) {
    val text = if (type == "For Sale") {
        view.context.getString(R.string.title_for_sale)
    } else {
        view.context.getString(R.string.title_for_rent)
    }
    view.text = text
}

@BindingAdapter("status")
fun houseStatus(view: TextView, status: String) {
    val text = if (status == "Ready For Sale") {
        view.context.getString(R.string.title_ready_for_sale)
    } else {
        view.context.getString(R.string.title_status_default)
    }
    view.text = text
}

@BindingAdapter("direction")
fun houseDirection(view: TextView, direction: String) {
    var text = direction.replace("East", "Đông", true)
    text = text.replace("West", "Tây", true)
    text = text.replace("South", "Nam", true)
    text = text.replace("North", "Bắc", true)
    val datas = text.split("-")
    val homeDirection = if (datas.size == 2) "${datas[1]} ${datas[0]}" else datas[0]
    view.text = homeDirection
}

@BindingAdapter("community")
fun joinCommunity(view: Button, communityId: Int) {
    val isJoin = MyApplication.sessionUser.value?.participants?.firstOrNull {
        it.community.id == communityId
    } != null
    if (isJoin) {
        view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.color_harp))
        view.text = view.context.resources.getString(R.string.title_out)
        view.setTextColor(ContextCompat.getColor(view.context, R.color.color_primary))
    } else {
        view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.color_primary))
        view.text = view.context.resources.getString(R.string.title_join)
        view.setTextColor(Color.WHITE)
    }
}