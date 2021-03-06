package com.apion.apionhome.utils

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.apion.apionhome.BuildConfig
import com.apion.apionhome.R
import com.apion.apionhome.utils.customview.actionsheet.ActionSheet
import com.apion.apionhome.utils.customview.actionsheet.callback.ActionSheetCallBack


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

fun Context.showAction(
    title: String,
    data: ArrayList<String>,
    actionSheetCallBack: ActionSheetCallBack,
    onCancel: () -> Unit
) {
    ActionSheet(this, data)
        .setTitle(title)
        .setCancelTitle("Hủy")
        .setColorTitleCancel(Color.parseColor("#1E7BF1"))
        .setColorTitle(Color.parseColor("#959595"))
        .setColorData(Color.parseColor("#1E7BF1"))
        .create(actionSheetCallBack, onCancel)
}

fun Context.hideKeyboard() {
    val imm: InputMethodManager =
        this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
}

fun Context.toMessenger(fbId: String?) {
    if (isAppInstalled("com.facebook.katana")) {
        try {
            val facebookIntent = Intent(Intent.ACTION_VIEW)
            facebookIntent.data = Uri.parse("fb://messaging/$fbId")
            startActivity(facebookIntent)
        } catch (e: Exception) {
            showToast(e.message?:"Đã có lỗi xảy ra!")
        }
    } else {
        Toast.makeText(
            applicationContext,
            "Messenger app not installing",
            Toast.LENGTH_SHORT
        ).show()
    }
}

fun Context.toFanpage(pageId: String?) {
    if (isAppInstalled("com.facebook.katana")) {
        try {
            val facebookIntent = Intent(Intent.ACTION_VIEW)
            facebookIntent.data = Uri.parse("fb://page/$pageId")
            startActivity(facebookIntent)
        } catch (e: Exception) {
            showToast(e.message?:"Đã có lỗi xảy ra!")
        }

    } else {
        val facebookIntent = Intent(Intent.ACTION_VIEW)
        facebookIntent.data = Uri.parse("https://www.facebook.com/$pageId")
        startActivity(facebookIntent)
    }
}

fun Context.toYoutube(channelId: String?) {
    try {
        val facebookIntent = Intent(Intent.ACTION_VIEW)
        facebookIntent.data = Uri.parse("https://www.youtube.com/c/$channelId")
        startActivity(facebookIntent)
    } catch (e: Exception) {
        showToast(e.message?:"Đã có lỗi xảy ra!")
    }
}

fun Context.toZalo(phone: String?) {
    if (isAppInstalled("com.zing.zalo")) {
        try {
            val zaloIntent = Intent(Intent.ACTION_VIEW)
            zaloIntent.data = Uri.parse("https://zalo.me/$phone")
            startActivity(zaloIntent)
        } catch (e: Exception) {
            showToast(e.message?:"Đã có lỗi xảy ra!")
        }
    } else {
        Toast.makeText(
            applicationContext,
            "Zalo app not installing",
            Toast.LENGTH_SHORT
        ).show()
    }
}

fun Context.isAppInstalled(packageName: String): Boolean {
    return try {
        applicationContext.packageManager.getApplicationInfo(packageName, 0)
        true
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        false
    }
}

fun Context.toPhone(phone: String?) {
    val intentDial = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone))
    startActivity(intentDial)
}

fun Context.toMessage(phone: String?) {
    val intent = Intent(Intent.ACTION_SENDTO)
    intent.data = Uri.parse("smsto:${phone}") // This ensures only SMS apps respond
    intent.putExtra("sms_body", "")
    startActivity(intent)
}

fun Context.shareApp() {
    try {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Apion Home")
        var shareMessage = "\nLet me recommend you this application\n\n"
        shareMessage =
            """
            ${shareMessage}https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}
            """.trimIndent()
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
        startActivity(Intent.createChooser(shareIntent, "choose one"))
    } catch (e: Exception) {
        e.printStackTrace()
    }
}