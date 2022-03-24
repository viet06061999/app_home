package com.apion.apionhome.ui.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.apion.apionhome.base.BindAbleAdapter

@Suppress("UNCHECKED_CAST")
@BindingAdapter("data")
fun <T> setRecyclerViewProperties(recyclerView: RecyclerView, data: List<T>?) {
    (recyclerView.adapter as? BindAbleAdapter<List<T>>)?.setData(data)
}

@Suppress("UNCHECKED_CAST")
@BindingAdapter("dataSlider")
fun <T> setRecyclerViewProperties(viewPager2: ViewPager2, data: List<T>?) {
    (viewPager2.adapter as? BindAbleAdapter<List<T>>)?.setData(data)
}