package com.apion.apionhome.base

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BindingActivity<T : ViewDataBinding> : AppCompatActivity() {
    @LayoutRes
    abstract fun getLayoutResId(): Int

    protected lateinit var binding: T
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutResId())

        if (Build.VERSION.SDK_INT in 21..29) {
            window.statusBarColor = Color.TRANSPARENT
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE

        } else if (Build.VERSION.SDK_INT >= 30) {
            ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, windowInsets ->

                val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
                view.layoutParams = (view.layoutParams as FrameLayout.LayoutParams).apply {
                    leftMargin = insets.left
                    bottomMargin = insets.bottom
                    rightMargin = insets.right
                }
                WindowInsetsCompat.CONSUMED
            }
        }


    }
}