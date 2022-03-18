package com.apion.apionhome.utils

import android.app.Dialog
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.apion.apionhome.R
import java.lang.Exception

fun Context.createProgressDialog() = Dialog(this).apply {
    setContentView(R.layout.progress_dialog)
    setCancelable(false)
    setCanceledOnTouchOutside(false)
}

fun Context.showToast(obj: Any) {
    val msg = when (obj) {
        is Int -> getString(obj)
        is Exception -> obj.message.toString()
        else -> obj.toString()
    }
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Context.hideKeyboard() {
    val imm: InputMethodManager =
        this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
}
