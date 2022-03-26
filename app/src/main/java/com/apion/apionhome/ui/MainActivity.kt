package com.apion.apionhome.ui

import android.os.Bundle
import android.view.View
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
        setContentView(binding.root)
        // khai báo 1 NavigationController điều hướng fragment có id = nav_host_fragment_activity_main
        val navController = this.findNavController(R.id.nav_host_fragment_activity_main)
//
//
//        // khai báo navView là 1 BottomNavigationView
        val navView: BottomNavigationView = binding.bottomNavigationView
        navView.background = null
        navView.menu.getItem(2).isEnabled = false
        navView.setupWithNavController(navController)
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
        }
    }

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
