package com.apion.apionhome.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ActivityNavigator
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.apion.apionhome.R
import com.apion.apionhome.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        window.statusBarColor = Color.TRANSPARENT
        setContentView(binding.root)


        // khai báo 1 NavigationController điều hướng fragment có id = nav_host_fragment_activity_main
        val navController = this.findNavController(R.id.nav_host_fragment_activity_main)


        // khai báo navView là 1 BottomNavigationView
        val navView: BottomNavigationView = binding.bottomNavigationView
//        navView.background = null
        navView.menu.getItem(2).isEnabled = false


        navView.setupWithNavController(navController)
    }

    override fun finish() {
        super.finish()
        ActivityNavigator.applyPopAnimationsToPendingTransition(this)
    }
}
