package com.apion.apionhome.ui.geting_started

import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.apion.apionhome.R
import com.apion.apionhome.base.BindingFragment
import com.apion.apionhome.base.RxViewModel
import com.apion.apionhome.databinding.FragmentVerifyPhoneBinding
import com.apion.apionhome.viewmodel.UserViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class VerifyPhoneFragment : BindingFragment<FragmentVerifyPhoneBinding>(FragmentVerifyPhoneBinding::inflate) {
    override val viewModel by sharedViewModel<UserViewModel>()
    override fun setupView() {
        binding.lifecycleOwner = this
        setListener()
        setupEdit()
    }
    fun setListener(){
        binding.imageBack.setOnClickListener {
            this.findNavController().popBackStack()
        }
    }

    private fun setupEdit() {
//        binding.etPassword1.isCursorVisible = true
        binding.etPassword2.isCursorVisible = false
        binding.etPassword3.isCursorVisible = false
        binding.etPassword4.isCursorVisible = false
        binding.etPassword5.isCursorVisible = false
        binding.etPassword6.isCursorVisible = false// ẩn con trỏ chuột

        binding.etPassword1.setOnKeyListener(
            PincodeFragment.GenericKeyEvent(
                binding.etPassword1,
                null
            )
        )
        binding.etPassword2.setOnKeyListener(
            PincodeFragment.GenericKeyEvent(
                binding.etPassword2,
                binding.etPassword1
            )
        )
        binding.etPassword3.setOnKeyListener(
            PincodeFragment.GenericKeyEvent(
                binding.etPassword3,
                binding.etPassword2
            )
        )
        binding.etPassword4.setOnKeyListener(
            PincodeFragment.GenericKeyEvent(
                binding.etPassword4,
                binding.etPassword3
            )
        )
        binding.etPassword5.setOnKeyListener(
            PincodeFragment.GenericKeyEvent(
                binding.etPassword5,
                binding.etPassword4
            )
        )
        binding.etPassword6.setOnKeyListener(
            PincodeFragment.GenericKeyEvent(
                binding.etPassword6,
                binding.etPassword5
            )
        )

        binding.etPassword1.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun afterTextChanged(s: Editable) {
                if (binding.etPassword1.text.toString().length == 1) //size as per your requirement
                {
                    binding.etPassword2.requestFocus()
                }
            }
        })
        binding.etPassword2.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun afterTextChanged(s: Editable) {
                if (binding.etPassword2.text.toString().length == 1) //size as per your requirement
                {
                    binding.etPassword3.requestFocus()
                }
            }
        })
        binding.etPassword3.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun afterTextChanged(s: Editable) {
                if (binding.etPassword3.text.toString().length == 1) //size as per your requirement
                {
                    binding.etPassword4.requestFocus()
                }
            }
        })
        binding.etPassword4.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun afterTextChanged(s: Editable) {
                if (binding.etPassword4.text.toString().length == 1) //size as per your requirement
                {
                    binding.etPassword5.requestFocus()
                }
            }
        })
        binding.etPassword5.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun afterTextChanged(s: Editable) {
                if (binding.etPassword5.text.toString().length == 1) //size as per your requirement
                {
                    binding.etPassword6.requestFocus()
                }
            }
        })

        binding.etPassword6.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun afterTextChanged(s: Editable) {
                if (binding.etPassword6.text.toString().length == 1) {
                    isValidVerify()
                }
            }
        })

        binding.btnDoneVerify.setOnClickListener {
            //isValidPinCode()
        }
    }
    private fun isValidVerify(){
        val pin = binding.etPassword1.text.toString() + binding.etPassword2.text.toString() +
                binding.etPassword3.text.toString() + binding.etPassword4.text.toString() +
                binding.etPassword5.text.toString() + binding.etPassword6.text.toString()
        println(viewModel.codeSent.value)
        val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(viewModel.codeSent.value ?: "", pin)

        signIn(credential)

    }
    private fun signIn (credential: PhoneAuthCredential) {
         var mAuth = FirebaseAuth.getInstance()
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener {
                    task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    println("Đăng nhập thành công")
                }
                else{
                    println("Xác thực thất bại")
                }
            }
    }

    class GenericKeyEvent internal constructor(
        private val currentView: EditText,
        private val previousView: EditText?
    ) : View.OnKeyListener {

        override fun onKey(p0: View?, keyCode: Int, event: KeyEvent?): Boolean {
            if (event!!.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL && currentView.id != R.id.etPassword1 && currentView.text.isEmpty()) {
                //If current is empty then previous EditText's number will also be deleted
                previousView!!.text = null
                currentView.isCursorVisible = false
                previousView.requestFocus()
                return true
            }
            return false
        }


    }
}