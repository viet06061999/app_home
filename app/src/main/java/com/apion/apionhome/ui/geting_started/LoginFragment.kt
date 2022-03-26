package com.apion.apionhome.ui.geting_started

import android.view.View
import androidx.navigation.fragment.findNavController
import com.apion.apionhome.R
import com.apion.apionhome.base.BindingFragment
import com.apion.apionhome.base.RxViewModel
import com.apion.apionhome.databinding.FragmentLoginBinding
import com.apion.apionhome.utils.isPhoneValid
import com.apion.apionhome.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

//class LoginFragment : BindingFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
class LoginFragment : BindingFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    override val viewModel by viewModel<UserViewModel>()// tại sao ko khai báo = UserViewModel()

    override fun setupView() {
        binding.lifecycleOwner = this
        binding.loginVM = viewModel
        listener()
        setupObserver()
    }

    private fun setupObserver(){
        viewModel.loginSuccess.observe(this) {
            if (it.first) {
                val action = LoginFragmentDirections.actionToPinCode(it.second)
                findNavController().navigate(action)
            }
        }
    }

    private fun listener() {
        binding.btnLogin.setOnClickListener(View.OnClickListener() {
            viewModel.login()
        })
        binding.textRegister.setOnClickListener {
            this.findNavController().navigate(R.id.actionToRegister)
        }
        viewModel.phone.observe(this) { error: String ->
            val errorText = if (error.isPhoneValid) null else "Định dạng không hợp lệ!"
            viewModel.setError(errorText)
            binding.editPhoneNumber.error = errorText
        }
    }
}