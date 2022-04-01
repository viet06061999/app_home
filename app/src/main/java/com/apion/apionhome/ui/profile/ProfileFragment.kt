package com.apion.apionhome.ui.profile

import com.apion.apionhome.base.BindingFragment
import com.apion.apionhome.base.RxViewModel
import com.apion.apionhome.databinding.FragmentProfileBinding
import com.apion.apionhome.viewmodel.ProfileViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BindingFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {
    override val viewModel by viewModel<ProfileViewModel>()

    override fun setupView() {
        binding.lifecycleOwner = this
        binding.profileVM = viewModel
    }
    override fun listener(){
        binding.btnLogout.setOnClickListener {

        }
    }
}