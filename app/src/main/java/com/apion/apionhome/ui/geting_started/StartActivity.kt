package com.apion.apionhome.ui.geting_started

import com.apion.apionhome.R
import com.apion.apionhome.base.BindingActivity
import com.apion.apionhome.databinding.ActivityStartBinding

class StartActivity : BindingActivity<ActivityStartBinding>() {
    override fun getLayoutResId() = R.layout.activity_start
    override fun setupView() {
        println("OK")
    }
}