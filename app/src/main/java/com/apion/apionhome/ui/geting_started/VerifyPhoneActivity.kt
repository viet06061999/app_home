package com.apion.apionhome.ui.geting_started

import android.app.AlertDialog
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navArgs
import com.apion.apionhome.MobileNavigationDirections
import com.apion.apionhome.MyApplication
import com.apion.apionhome.R
import com.apion.apionhome.base.BindingActivity
import com.apion.apionhome.base.BindingFragment
import com.apion.apionhome.base.RxViewModel
import com.apion.apionhome.data.model.User
import com.apion.apionhome.databinding.ActivityVerifyPhoneBinding
import com.apion.apionhome.utils.createProgressDialog
import com.apion.apionhome.viewmodel.UserViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class VerifyPhoneActivity : BindingActivity<ActivityVerifyPhoneBinding>() {
    val viewModel by viewModel<UserViewModel>()
    val dialog by lazy {
        this.createProgressDialog()
    }
    override fun setupView() {
        binding.lifecycleOwner = this
        binding.verifyVM = viewModel
        val user = intent.extras?.getSerializable("USER") as User
//        val verifyId = intent.extras?.getSerializable("verifyId") as String

        viewModel.phoneRegister.value = user.phone
//        viewModel.codeSent.value = verifyId
//        if(result.toString() == "true"){
//            setDialog1()
//        }
        println("CHECK")
        println(user.toString())
        setListener()
        setupEdit()
    }
    fun setListener(){
        binding.imageBack.setOnClickListener {
            this.finish()
        }
        binding.txtAnswer.setOnClickListener {

        }
    }
//    private fun setDialog1(){
//        val dialog = AlertDialog.Builder(this)
//        dialog.setMessage("Đã có lỗi xảy ra")
//        dialog.setPositiveButton("Đóng") { _, _ ->
//           this.finish()
////            navController.navigate(R.id.actionToLogin)
//        }
//        dialog.show()
//    }
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
                    viewModel._isLoading.value = true
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
        println("PHAM ANH TUAN")


        viewModel.codeSent.value?. let{
            val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(viewModel.codeSent.value ?: "", pin)

            signIn(credential)
        }


    }
    private fun signIn (credential: PhoneAuthCredential) {
         var mAuth = FirebaseAuth.getInstance()
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener {
                    task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    println("Đăng nhập thành công")
                    viewModel.register()
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

    override fun getLayoutResId() : Int {
        return R.layout.activity_verify_phone
    }
}