package com.apion.apionhome.ui.geting_started

import androidx.navigation.fragment.findNavController
import com.apion.apionhome.base.BindingFragment
import com.apion.apionhome.base.RxViewModel
import com.apion.apionhome.databinding.FragmentGetPincodeNewBinding
import com.apion.apionhome.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class GetNewPincodeFragment : BindingFragment<FragmentGetPincodeNewBinding>(FragmentGetPincodeNewBinding::inflate) {
    override val viewModel by sharedViewModel<UserViewModel>()
    override fun setupView() {
        binding.lifecycleOwner = this
        binding.getPincodeVM   = viewModel
        setupListener()
    }
    fun setupListener(){
        binding.btnChangePincode.setOnClickListener {

        }
        binding.imageBackForget.setOnClickListener {
            this.findNavController().popBackStack()
        }
    }
}