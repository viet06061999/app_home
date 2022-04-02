package com.apion.apionhome.ui.notification

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.apion.apionhome.MobileNavigationDirections
import com.apion.apionhome.base.BindingFragment
import com.apion.apionhome.data.model.House
import com.apion.apionhome.databinding.FragmentNotificationBinding
import com.apion.apionhome.ui.adapter.NotificationAdapter
import com.apion.apionhome.viewmodel.HouseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotificationFragment :
    BindingFragment<FragmentNotificationBinding>(FragmentNotificationBinding::inflate) {

    override val viewModel by viewModel<HouseViewModel>()
    private val adapter = NotificationAdapter(::onItemClick)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getNotification(null)
    }

    override fun setupView() {
        binding.lifecycleOwner = this
        binding.notificationVM = viewModel
        binding.recyclerViewNotification.adapter = adapter
        setupRefresh()
    }

    private fun setupRefresh(){
        binding.notificationRefresh.apply {
            //binding.layoutHeader.root.viewTreeObserver.addOnGlobalLayoutListener(observerVisible)
            setOnRefreshListener {
                viewModel.getNotification {
                   this.isRefreshing = false
                }
            }
        }
    }

    private fun onItemClick(house: House) {
        val action = MobileNavigationDirections.actionMainToDetail(house)
        findNavController().navigate(action)
    }
}