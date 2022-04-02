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
    private val args: PincodeFragmentArgs by navArgs()

    override fun setupView() {
        binding.lifecycleOwner = this
        setupEdit()
        println(args.pinCode)
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
                ::isValidPinCode)
        )
        binding.etPassword2.setOnKeyListener(
            GenericKeyEvent(
                binding.etPassword3,
                binding.etPassword2,
                binding.etPassword1,
                ::isValidPinCode
            )
        )
        binding.etPassword3.setOnKeyListener(
            GenericKeyEvent(
                binding.etPassword4,
                binding.etPassword3,
                binding.etPassword2,
                ::isValidPinCode
            )
        )
        binding.etPassword4.setOnKeyListener(
            GenericKeyEvent(
                null,
                binding.etPassword4,
                binding.etPassword3,
                ::isValidPinCode
            )
        )



        binding.buttonlogin.setOnClickListener {
            isValidPinCode()
        }
    }

    private fun isValidPinCode() {
        val pin =
            binding.etPassword1.text.toString() + binding.etPassword2.text.toString() + binding.etPassword3.text.toString() + binding.etPassword4.text.toString()
        if (args.pinCode == pin){
            findNavController().navigate(PincodeFragmentDirections.actionToMain())
            requireActivity().finish()
        }
    }
    private fun checkPincode() : Boolean{
        val pin =
            binding.etPassword1.text.toString() + binding.etPassword2.text.toString() + binding.etPassword3.text.toString() + binding.etPassword4.text.toString()
        if (args.pinCode == pin){
            return true
        }else{
            return false
        }
    }



    class GenericKeyEvent internal constructor(
        private val nextView: EditText?,
        private val currentView: EditText,
        private val previousView: EditText?,
        private val isValidVerify : ()->Unit
    ) : View.OnKeyListener {

        override fun onKey(p0: View?, keyCode: Int, event: KeyEvent?): Boolean {
            if(currentView.id == R.id.etPassword1){
                currentView.isCursorVisible = true
            }
            if (event!!.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL && currentView.id != R.id.etPassword1 && currentView.text.isEmpty()) {
                //If current is empty then previous EditText's number will also be deleted
                previousView!!.text = null
                currentView.isCursorVisible = false
                currentView.isEnabled = false
                previousView.isEnabled = true
                previousView.requestFocus()
                return true
            }
            if (  currentView.id != R.id.etPassword6 && !currentView.text.isEmpty() ) {
                if(keyCode == KeyEvent.KEYCODE_0 || keyCode == KeyEvent.KEYCODE_1 || keyCode == KeyEvent.KEYCODE_2 ||
                    keyCode == KeyEvent.KEYCODE_3 || keyCode == KeyEvent.KEYCODE_4 || keyCode == KeyEvent.KEYCODE_5 ||
                    keyCode == KeyEvent.KEYCODE_6 || keyCode == KeyEvent.KEYCODE_7 || keyCode == KeyEvent.KEYCODE_8 || keyCode == KeyEvent.KEYCODE_9
                ){
                    currentView.setText((keyCode-7).toString())
                }

                nextView!!.isEnabled = true
                nextView!!.requestFocus()
                currentView.isCursorVisible = false
                currentView.isEnabled = false
//                previousView.requestFocus()
                return true
            }
            if(currentView.id == R.id.etPassword6 && !currentView.text.isEmpty() ){
                isValidVerify()
            }
            return false
        }


    }
}