package com.apion.apionhome.ui.geting_started

import androidx.navigation.fragment.findNavController
import com.apion.apionhome.R
import com.apion.apionhome.base.BindingFragment
import com.apion.apionhome.base.RxViewModel
import com.apion.apionhome.databinding.FragmentForgetPincodeBinding
import com.apion.apionhome.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PhoneForgetFragment : BindingFragment<FragmentForgetPincodeBinding>(FragmentForgetPincodeBinding::inflate) {
    override val viewModel by sharedViewModel<UserViewModel>()
    override fun setupView() {
        binding.lifecycleOwner = this
        binding.forgetVM = viewModel
        setupListener()
    }
    private fun setupListener(){
        binding.imageBackForget.setOnClickListener {
            this.findNavController().popBackStack()
        }
        binding.btnSentForget.setOnClickListener {
            this.findNavController().navigate(R.id.actionToGetNewPincode)
        }
    }
}