package com.apion.apionhome.ui.geting_started

import androidx.navigation.fragment.findNavController
import com.apion.apionhome.R
import com.apion.apionhome.base.BindingFragment
import com.apion.apionhome.base.RxViewModel
import com.apion.apionhome.databinding.FragmentLoginBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BindingFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    override val viewModel by viewModel<RxViewModel>()

    override fun setupView() {
        listener()
    }

    private fun listener() {
        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.actionToPinCode)
        }
    }
}