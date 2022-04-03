package com.apion.apionhome.ui.geting_started

import androidx.navigation.fragment.findNavController
import com.apion.apionhome.R
import com.apion.apionhome.base.BindingFragment
import com.apion.apionhome.databinding.FragmentForgetPincodeBinding
import com.apion.apionhome.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PhoneForgetFragment :
    BindingFragment<FragmentForgetPincodeBinding>(FragmentForgetPincodeBinding::inflate) {

    override val viewModel by viewModel<UserViewModel>()

    override fun setupView() {
        binding.lifecycleOwner = this
        binding.forgetVM = viewModel
        setupListener()
        viewModel.checkExistPhone.observe(this) {
            if ("Số điện thoại này đã tồn tại." == viewModel.textCheckExistPhone.value && it == true) {
                findNavController().navigate(R.id.actionToVerifyPhone)
            } else {
                showDialogCustom(null,
                    viewModel.textCheckExistPhone.value
                        ?: "Số điện thoại này không tồn tại trên hệ thông!",
                    null
                ) {
                }
            }
        }
    }

    private fun setupListener() {
        binding.imageBackForget.setOnClickListener {
            this.findNavController().popBackStack()
        }
        binding.btnSentForget.setOnClickListener {
            if (viewModel.phoneRegister.value.isNullOrBlank()) {
                viewModel.phoneRule.value = "Vui lòng nhập số điện thoại"
            } else {
                viewModel.checkExits()
            }
//            this.findNavController().navigate(R.id.actionToGetNewPincode)
        }
    }
}
