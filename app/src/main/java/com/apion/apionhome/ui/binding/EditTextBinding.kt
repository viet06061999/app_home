package com.apion.apionhome.ui.binding

import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("errorText")
fun TextInputLayout.bindError(errorText: String?) {
    println("error $errorText")
    error = errorText
}