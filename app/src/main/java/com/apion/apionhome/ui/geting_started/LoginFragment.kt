package com.apion.apionhome.ui.geting_started

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.apion.apionhome.R
import com.apion.apionhome.base.BindingFragment
import com.apion.apionhome.databinding.FragmentLoginBinding
import com.apion.apionhome.utils.isPhoneValid
import com.apion.apionhome.viewmodel.LoginViewModel
import com.apion.apionhome.viewmodel.UpdatePincodeViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

//class LoginFragment : BindingFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
class LoginFragment : BindingFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    override val viewModel by sharedViewModel<LoginViewModel>()// tại sao ko khai báo = UserViewModel()

    lateinit var mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    lateinit var mAuth: FirebaseAuth

    override fun setupView() {
        binding.lifecycleOwner    = this
        binding.loginVM           = viewModel
        viewModel.errorText.value = null
        viewModel._loginSuccess.value = Pair(false,"")
        listener()
        setupObserver()
    }

    private fun setupObserver(){
        viewModel.loginSuccess.observe(this) {
            if (it.first) {
                val mySharedPreferences : SharedPreferences = requireActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
                var checkExist = mySharedPreferences.getString(viewModel.phone.value,"")
                if(checkExist.equals("phone")){
                    // Đã tồn tại
//                        viewModel.
                    findNavController().navigate(
                        R.id.actionToPinCode,
                        bundleOf(
                            "pincode" to it.second
                        )
                    )
                }else{
                    // Chưa từng đăng nhập  trên máy
                        verify ()
                }
                        println(viewModel.phone.value)
                }
//                findNavController().navigate(action)
            }

        viewModel.phone.observe(this) { error: String ->
            val errorText = if (error.isPhoneValid) null else "Định dạng không hợp lệ!"
            viewModel.setError(errorText)
            binding.editPhoneNumber.error = errorText
        }
        viewModel.errorLogin.observe(this){
            it?.let{
                val dialog = AlertDialog.Builder(requireContext())
                dialog.setTitle("Thông báo")
                dialog.setMessage(it)
                dialog.setPositiveButton("Đóng"){ _, _ ->
                }
                dialog.show()
            }
        }
    }

    private fun listener() {
        binding.btnLogin.setOnClickListener(View.OnClickListener() {
            viewModel.login()
        })
        binding.textRegister.setOnClickListener {
            this.findNavController().navigate(R.id.actionToVerifyPhone)
        }
        binding.txtPhoneForget.setOnClickListener {
            this.findNavController().navigate(R.id.actionToPhoneForget)
        }

    }

    private fun verificationCallbacks () {
        mCallbacks = object: PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {

            }
            override fun onVerificationFailed(p0: FirebaseException) {
                println(p0)
                val dialog = AlertDialog.Builder(requireContext())
                dialog.setTitle("Thông báo")
                dialog.setMessage(p0.message)
                dialog.setPositiveButton("Đóng") { _, _ ->
                    viewModel._isLoading.value = false
                }
                dialog.show()
            }
            override fun onCodeSent(verfication: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(verfication, p1)
                viewModel._isLoading.value = false
                viewModel.codeSent.value   =  verfication

                val checkIsFirst =  viewModel.isFirst.value
                //
                checkIsFirst?.let {
                    findNavController().navigate(
                        R.id.actionToVerifyLogin,
                        bundleOf(
                            "pincode" to viewModel.loginSuccess.value?.second,
                            "phone" to viewModel.phone.value,
                            "codeSent" to verfication,
                            "isFirst" to it.toString(),
                            "idUser" to viewModel.user.value!!.id.toString()
                        )
                    )

                }
            }
        }
    }
    private fun verify (){
        verificationCallbacks()
        mAuth = FirebaseAuth.getInstance()
        viewModel._isLoading.value = true
//        mAuth.getFirebaseAuthSettings().setAppVerificationDisabledForTesting(true)
        val options = PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber(viewModel.getPhoneLogin()) // Phone number to verify
            .setTimeout(120, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity()) // Activity (for callback binding)
            .setCallbacks(mCallbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }
}