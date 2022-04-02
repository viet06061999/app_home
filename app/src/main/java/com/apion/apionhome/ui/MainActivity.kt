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
import com.apion.apionhome.utils.TabApp
import com.apion.apionhome.utils.createProgressDialog
import com.apion.apionhome.utils.showToast
import com.apion.apionhome.viewmodel.HouseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var navView: BottomNavigationView
    private var currentIndex = 0
    private val viewModel by viewModel<HouseViewModel>()
    private lateinit var navController: NavController
    val dialogLoading by lazy {
        createProgressDialog()
    }
    private val dialog by lazy {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Yêu cầu đăng nhập!")
        dialog.setMessage("Vui lòng đăng nhập để sử dụng tính năng này!")
        dialog.setPositiveButton("Đăng nhập") { _, _ ->
            navView.menu.getItem(currentIndex).isChecked = true
            navController.navigate(MobileNavigationDirections.actionToLogin())
        }
        dialog.setNegativeButton(getString(R.string.tittle_button_cancel)) { dialogShow, _ ->
            navView.menu.getItem(currentIndex).isChecked = true
            MyApplication.tabToNavigate.value = null
            dialogShow.dismiss()
        }
        dialog
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = this.findNavController(R.id.nav_host_fragment_activity_main)
        navView = binding.bottomNavigationView
        navView.menu.getItem(2).isEnabled = false
        setupNotificationPush()
        setupToCurrentTab()
        setupListener()
        setupObserver()
    }

    private fun setupObserver(){
        viewModel.errorException.observe(this) {
            if (it!=null){
                showToast(getString(R.string.default_error))
            }
        }
        viewModel.isLoading.observe(this) {
            if (it) dialogLoading.show() else dialogLoading.dismiss()
        }
    }
    private fun setupListener() {
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
                        println("da vao den đây")
                        navController.navigate(R.id.actionToNotification)
                    } else {
                        MyApplication.tabToNavigate.value = 3
                        dialog.show()
                    }
                }
                R.id.navigation_profile ->{
                    if (MyApplication.sessionUser.value != null) {
                        currentIndex = 4
                        println("da vao den đây")
                        navController.navigate(R.id.actionToProfile)
                    } else {
                        MyApplication.tabToNavigate.value = 4
                        dialog.show()
                    }
                }
            }
            true
        }
        binding.fab.setOnClickListener {
            if (MyApplication.sessionUser.value != null) {
                currentIndex = 2
                navController.navigate(R.id.actionToAdd)
            } else {
                MyApplication.tabToNavigate.value = 2
                dialog.show()
            }
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id in arrayListOf(
                    R.id.searDetailResultFragment,
                    R.id.selectLocationFragment,
                    R.id.personProfileFragment
                )
            ) {
                hideBottom()
            } else {
                showBottom()
            }
            when (destination.id) {
                R.id.navigation_home -> {
                    navView.menu.getItem(0).isChecked = true
                }
            }
        }
    }

    private fun setupNotificationPush() {
        val houseId = intent.getStringExtra("house_id")
        houseId?.let {
            viewModel.getHouseById(houseId.toInt())
            viewModel.house.observe(this) {
                val action = MobileNavigationDirections.actionMainToDetail(it)
                navController.navigate(action)
            }
        }
    }

    private fun setupToCurrentTab() {
        when (MyApplication.tabToNavigate.value) {
            TabApp.NOTIFICATION -> {
                println("sang noti")
                navView.menu.getItem(3).isChecked = true
                MyApplication.tabToNavigate.value = null
                navController.navigate(R.id.actionToNotification)
            }
            TabApp.CREATE_HOUSE -> {
                navView.menu.getItem(2).isChecked = true
                MyApplication.tabToNavigate.value = null
                println("da vao den 2")
                navController.navigate(R.id.actionToAdd)
            }
            TabApp.PROFILE_PERSON -> {
                MyApplication.tabToNavigate.value = null
                MyApplication.profileUserNavigate.value?.let {
                    val action = MobileNavigationDirections.actionToPersonProfile(it)
                    navController.navigate(action)
                }
            }
            TabApp.DETAIL_HOUSE -> {
                MyApplication.tabToNavigate.value = null
                MyApplication.houseNavigate.value?.let {
                    val action = MobileNavigationDirections.actionMainToDetail(it)
                    navController.navigate(action)
                }
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
