package com.apion.apionhome.ui.geting_started

import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.apion.apionhome.MyApplication
import com.apion.apionhome.R
import com.apion.apionhome.base.BindingFragment
import com.apion.apionhome.base.RxViewModel
import com.apion.apionhome.databinding.FragmentPincodeBinding
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import org.koin.androidx.viewmodel.ext.android.viewModel

class PincodeFragment : BindingFragment<FragmentPincodeBinding>(FragmentPincodeBinding::inflate) {

    override val viewModel by viewModel<RxViewModel>()

    override fun setupView() {
        binding.lifecycleOwner = this
        setupEdit()
        setupListener()
    }

    private fun setupEdit() {
        binding.etPassword1.isCursorVisible = true
        binding.etPassword2.isCursorVisible = false
        binding.etPassword3.isCursorVisible = false
        binding.etPassword4.isCursorVisible = false

        binding.etPassword1.isEnabled = true
        binding.etPassword2.isEnabled = false
        binding.etPassword3.isEnabled = false
        binding.etPassword4.isEnabled = false


        binding.etPassword1.setOnKeyListener(
            GenericKeyEvent(
                binding.etPassword2,
                binding.etPassword1,
                null,
                ::isValidPinCode,
            )
        )
        binding.etPassword2.setOnKeyListener(
            GenericKeyEvent(
                binding.etPassword3,
                binding.etPassword2,
                binding.etPassword1,
                ::isValidPinCode,
            )
        )
        binding.etPassword3.setOnKeyListener(
            GenericKeyEvent(
                binding.etPassword4,
                binding.etPassword3,
                binding.etPassword2,
                ::isValidPinCode,
            )
        )
        binding.etPassword4.setOnKeyListener(
            GenericKeyEvent(
                null,
                binding.etPassword4,
                binding.etPassword3,
                ::isValidPinCode,
            )
        )



    }
    private fun setupListener(){
        binding.buttonlogin.setOnClickListener {
//            this.findNavController().navigate(R.id.actionToLogin)
            println("BẠN VỪA CLICK ĐĂNG NHẬP")
        }

    }
    private fun isValidPinCode() {
        val pincode    = arguments?.getString("phone", "") ?: ""

        val pin = binding.etPassword1.text.toString() + binding.etPassword2.text.toString() + binding.etPassword3.text.toString() + binding.etPassword4.text.toString()
        if (pincode == pin){
            findNavController().navigate(PincodeFragmentDirections.actionToMain())
            requireActivity().finish()
        }
    }
//    private fun checkPincode() : Boolean{
//        val pin =
//            binding.etPassword1.text.toString() + binding.etPassword2.text.toString() + binding.etPassword3.text.toString() + binding.etPassword4.text.toString()
//        if (pincode == pin){
//            return true
//        }else{
//            return false
//        }
//    }



    class GenericKeyEvent internal constructor(
        private val nextView: EditText?,
        private val currentView: EditText,
        private val previousView: EditText?,
        private val isValidVerify : ()->Unit
    ) : View.OnKeyListener {

        override fun onKey(p0: View?, keyCode: Int, event: KeyEvent?): Boolean {
            if (currentView.id == R.id.etPassword1) {
                currentView.isCursorVisible = true

            }

            if (event!!.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL && currentView.id != R.id.etPassword1 && currentView.text.isEmpty()) {
                //If current is empty then previous EditText's number will also be deleted
                previousView!!.text = null
                previousView.isCursorVisible = true
                previousView.isEnabled = true
                previousView.requestFocus()

                currentView.isEnabled = false

                return true
            }

            if (event!!.action == KeyEvent.ACTION_UP &&currentView.id != R.id.etPassword4 && !currentView.text.isEmpty()) {
                nextView!!.isEnabled = true
                nextView!!.isCursorVisible = true


                nextView!!.requestFocus()

                currentView.isCursorVisible = true
                currentView.isEnabled = false
                println("AAAAAAAAA")

                return false
//                previousView.requestFocus()
            }


            return false
        }
    }
}
