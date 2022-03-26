package com.apion.apionhome.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.apion.apionhome.MobileNavigationDirections
import com.apion.apionhome.MyApplication
import com.apion.apionhome.R
import com.apion.apionhome.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var navView: BottomNavigationView
    private var currentIndex = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // khai báo 1 NavigationController điều hướng fragment có id = nav_host_fragment_activity_main
       val navController = this.findNavController(R.id.nav_host_fragment_activity_main)

        // khai báo navView là 1 BottomNavigationView
        navView = binding.bottomNavigationView
//        navView.background = null
        navView.menu.getItem(2).isEnabled = false
//        navView.setupWithNavController(navController)
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Yêu cầu đăng nhập!")
        dialog.setMessage("Vui lòng đăng nhập để sử dụng tính năng này!")
        dialog.setPositiveButton("Đăng nhập") { _, _ ->
            navView.menu.getItem(currentIndex).isChecked = true
            navController.navigate(MobileNavigationDirections.actionToLogin())
        }
        dialog.setNegativeButton(getString(R.string.tittle_button_cancel)) { dialogShow, _ ->
            navView.menu.getItem(currentIndex).isChecked = true
            dialogShow.dismiss()
        }
        navView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    navController.navigate(R.id.actionToHome)
                    currentIndex = 0
                }
                R.id.navigation_search -> {
                    navController.navigate(R.id.actionToSearch)
                    currentIndex = 1
                }
                R.id.navigation_notification -> {
                    if (MyApplication.sessionUser.value != null) {
                        currentIndex = 3
                        navController.navigate(R.id.actionToNotification)
                    } else {
                        dialog.show()
                    }
                }
            }
            true
        }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id in arrayListOf(
                    R.id.searDetailResultFragment,
                    R.id.selectLocationFragment
                )
            ) {
                hideBottom()
            } else {
                showBottom()
            }
            when(destination.id){
                R.id.navigation_home ->{
                    navView.menu.getItem(0).isChecked = true
                }
            }
        }
    }

//    override fun onBackPressed() {
//        super.onBackPressed()
//        navController.backStack.clear()
//    }

    private fun hideBottom() {
        binding.bottomNavigationView.visibility = View.GONE
        binding.fab.visibility = View.GONE
    }

    private fun showBottom() {
        binding.bottomNavigationView.visibility = View.VISIBLE
        binding.fab.visibility = View.VISIBLE
    }

    override fun finish() {
        super.finish()
        ActivityNavigator.applyPopAnimationsToPendingTransition(this)
    }
}
