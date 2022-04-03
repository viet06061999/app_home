package com.apion.apionhome.ui.geting_started

import androidx.navigation.fragment.findNavController
import com.apion.apionhome.R
import com.apion.apionhome.base.BindingFragment
import com.apion.apionhome.databinding.FragmentGetPincodeNewBinding
import com.apion.apionhome.viewmodel.ProfileViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class GetNewPincodeFragment :
    BindingFragment<FragmentGetPincodeNewBinding>(FragmentGetPincodeNewBinding::inflate) {
    override val viewModel by viewModel<ProfileViewModel>()
    private val userId by lazy {
        arguments?.getString("userId", null)
    }

    override fun setupView() {
        binding.lifecycleOwner = this
        binding.getPincodeVM = viewModel
        setupListener()
        viewModel.updatePinCodeDone.observe(this){
            if(it){
                showToast("Đổi pin code thành công!")
                findNavController().navigate(R.id.actionToLogin)
            }
        }
    }

    fun setupListener() {
        binding.btnChangePincode.setOnClickListener {
            viewModel.updatePin(userId ?: "")
        }
        binding.imageBackForget.setOnClickListener {
            this.findNavController().popBackStack()
        }
    }
}