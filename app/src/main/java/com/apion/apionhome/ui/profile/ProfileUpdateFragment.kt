package com.apion.apionhome.ui.profile

import android.os.Build
import android.view.View
import android.view.WindowInsetsController
import androidx.navigation.fragment.findNavController
import com.apion.apionhome.MyApplication
import com.apion.apionhome.base.BindingFragment
import com.apion.apionhome.databinding.FragmentLevelBinding
import com.apion.apionhome.databinding.FragmentUpdateProfileBinding
import com.apion.apionhome.viewmodel.ProfileViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileUpdateFragment :
    BindingFragment<FragmentUpdateProfileBinding>(FragmentUpdateProfileBinding::inflate) {
    override val viewModel by viewModel<ProfileViewModel>()

    override fun setupView() {
        binding.lifecycleOwner = this
        binding.user = MyApplication.sessionUser.value
        viewModel.email.value = MyApplication.sessionUser.value?.email
        viewModel.fbId.value = MyApplication.sessionUser.value?.facebook_id
        binding.userProfileVM = viewModel
        binding.btnDone.setOnClickListener {
            viewModel.updateUser(binding.user)
        }
        binding.btnAfter.setOnClickListener {
            findNavController().popBackStack()
        }
        viewModel.updateDone.observe(this) {
            if(it){
                showToast("Đã cập nhật thông tin!")
                findNavController().popBackStack()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val view = requireActivity().window.decorView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            requireActivity().window.decorView.windowInsetsController?.hide(WindowInsets.Type.statusBars())
            view.windowInsetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        } else {
            view.systemUiVisibility =
                view.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    override fun onStop() {
        super.onStop()
        val view = requireActivity().window.decorView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            view.windowInsetsController?.setSystemBarsAppearance(
                0,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        } else {
            var flags = view.systemUiVisibility
            flags = flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            view.systemUiVisibility = flags
        }
    }
}