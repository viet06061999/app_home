package com.apion.apionhome.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.apion.apionhome.R
import com.apion.apionhome.utils.customview.actionsheet.ActionSheet
import com.apion.apionhome.utils.customview.actionsheet.callback.ActionSheetCallBack
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

fun Context.showAction(title:String, data: ArrayList<String>, actionSheetCallBack: ActionSheetCallBack){
    ActionSheet(this, data)
        .setTitle(title)
        .setCancelTitle("Hủy")
        .setColorTitleCancel(Color.parseColor("#1E7BF1"))
        .setColorTitle(Color.parseColor("#959595"))
        .setColorData(Color.parseColor("#1E7BF1"))
        .create(actionSheetCallBack)
}

fun Context.hideKeyboard() {
    val imm: InputMethodManager =
        this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
}
