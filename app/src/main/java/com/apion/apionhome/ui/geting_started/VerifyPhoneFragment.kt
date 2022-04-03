package com.apion.apionhome.ui.geting_started

import android.app.AlertDialog
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.KeyEvent.*
import android.view.View
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.apion.apionhome.R
import com.apion.apionhome.StartNavigationDirections
import com.apion.apionhome.base.BindingFragment
import com.apion.apionhome.databinding.FragmentVerifyPhoneBinding
import com.apion.apionhome.ui.binding.bindError
import com.apion.apionhome.viewmodel.UserViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class VerifyPhoneFragment : BindingFragment<FragmentVerifyPhoneBinding>(FragmentVerifyPhoneBinding::inflate) {
    override val viewModel by sharedViewModel<UserViewModel>()
    val timer = object : CountDownTimer(120000, 1000) {

        override fun onTick(millisUntilFinished: Long) {
            val second = millisUntilFinished / 1000
            var minute = second / 60
            if (second % 60 < 10) {
                binding.txtWarning.setText("Gửi lại mã xác thực sau: 0" + minute + ":0" + second % 60)
            } else {
                binding.txtWarning.setText("Gửi lại mã xác thực sau: 0" + minute + ":" + second % 60)
            }

        }

        override fun onFinish() {
            binding.txtWarning.setText("done!")
        }
    }

    override fun setupView() {
        binding.lifecycleOwner = this
        binding.verifyVM = viewModel
        viewModel._isLoading.value = false
        timer.start()
//        val verifyId = intent.extras?.getSerializable("verifyId") as String

//        viewModel.codeSent.value = verifyId
//        if(result.toString() == "true"){
//            setDialog1()
//        }
        println("CHECK")
        setListener()
        setupObserver()
        setupEdit()
    }

    fun setListener() {
        binding.txtAnswer.setOnClickListener {

        }
    }

    private fun setupObserver() {
        viewModel.registerSuccess.observe(this) {
            setDialogCreateSuccess()
        }
        viewModel.errorRegister.observe(this) {
            it?.let {
                setRegisterFailed(it)
            }
//            println("ĐÃ CÓ LỖI XẢY RA GỬI API")
        }
    }

    private fun setRegisterFailed(error: String) {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setTitle("Thông báo")
        dialog.setMessage(error)
        dialog.setPositiveButton("Đóng") { _, _ ->
            findNavController().popBackStack()
        }
        dialog.show()
    }

    private fun setDialogCreateSuccess() {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setTitle("Thông báo")
        dialog.setMessage("Đăng ký thành công.")
        dialog.setPositiveButton("Đóng") { _, _ ->
            findNavController().navigate(StartNavigationDirections.actionToLogin())
        }
        dialog.show()
    }

    private fun setupEdit() {
        binding.etPassword1.isCursorVisible = true
        binding.etPassword2.isCursorVisible = false
        binding.etPassword3.isCursorVisible = false
        binding.etPassword4.isCursorVisible = false
        binding.etPassword5.isCursorVisible = false
        binding.etPassword6.isCursorVisible = false// ẩn con trỏ chuột

        binding.etPassword1.isEnabled = true
        binding.etPassword2.isEnabled = false
        binding.etPassword3.isEnabled = false
        binding.etPassword4.isEnabled = false
        binding.etPassword5.isEnabled = false
        binding.etPassword6.isEnabled = false

        binding.etPassword1.setOnKeyListener(
            GenericKeyEvent(
                binding.etPassword2,
                binding.etPassword1,
                null,
                ::isValidVerify
            )
        )
        binding.etPassword2.setOnKeyListener(
            GenericKeyEvent(
                binding.etPassword3,
                binding.etPassword2,
                binding.etPassword1,
                ::isValidVerify
            )
        )
        binding.etPassword3.setOnKeyListener(
            GenericKeyEvent(
                binding.etPassword4,
                binding.etPassword3,
                binding.etPassword2,
                ::isValidVerify
            )
        )
        binding.etPassword4.setOnKeyListener(
            GenericKeyEvent(
                binding.etPassword5,
                binding.etPassword4,
                binding.etPassword3,
                ::isValidVerify
            )
        )
        binding.etPassword5.setOnKeyListener(
            GenericKeyEvent(
                binding.etPassword6,
                binding.etPassword5,
                binding.etPassword4,
                ::isValidVerify
            )
        )
        binding.etPassword6.setOnKeyListener(
            GenericKeyEvent(
                null,
                binding.etPassword6,
                binding.etPassword5,
                ::isValidVerify
            )
        )


        binding.btnDoneVerify.setOnClickListener {
            //isValidPinCode()
        }
    }

    fun isValidVerify() {
        viewModel._isLoading.value = true
        val pin = binding.etPassword1.text.toString() + binding.etPassword2.text.toString() +
                binding.etPassword3.text.toString() + binding.etPassword4.text.toString() +
                binding.etPassword5.text.toString() + binding.etPassword6.text.toString()
        println("CODE SENT: ")
        println(viewModel.codeSent.value)
        viewModel.codeSent.value?.let {
            val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(it, pin)
            signIn(credential)
        }

    }

    private fun signIn(credential: PhoneAuthCredential) {
        var mAuth = FirebaseAuth.getInstance()
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener { task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    println("Xác thực thành công")
                    viewModel.register()
                } else {
                    viewModel._isLoading.value = false
                    val dialog = AlertDialog.Builder(requireContext())
                    dialog.setTitle("Thông báo ")
                    dialog.setMessage("Xác thực thất bại. Vui lòng thử lại!")
                    dialog.setPositiveButton("Đóng") { _, _ -> }
                    dialog.show()
                }
            }
    }


    class GenericKeyEvent internal constructor(
        private val nextView: EditText?,
        private val currentView: EditText,
        private val previousView: EditText?,
        private val isValidVerify: () -> Unit
    ) : View.OnKeyListener {

        override fun onKey(p0: View?, keyCode: Int, event: KeyEvent?): Boolean {
            if (currentView.id == R.id.etPassword1) {
                currentView.isCursorVisible = true
            }
//            if (event!!.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL && currentView.id != R.id.etPassword1 && currentView.text.isEmpty()) {
//                //If current is empty then previous EditText's number will also be deleted
//                previousView!!.text = null
//                currentView.isCursorVisible = false
//                currentView.isEnabled = false
//                previousView.isEnabled = true
//                previousView.requestFocus()
//                println("PreviousView: + ${previousView.text}")
//            }
//            if(!currentView.text.isEmpty()) {
//                if (keyCode == KEYCODE_0 || keyCode == KEYCODE_1 || keyCode == KEYCODE_2 ||
//                    keyCode == KEYCODE_3 || keyCode == KEYCODE_4 || keyCode == KEYCODE_5 ||
//                    keyCode == KEYCODE_6 || keyCode == KEYCODE_7 || keyCode == KEYCODE_8 || keyCode == KEYCODE_9
//                ) {
//                    currentView.setText((keyCode - 7).toString())
//                }
//            }
            if (currentView.id != R.id.etPassword6 && !currentView.text.isEmpty()) {
                nextView!!.isEnabled = true

                nextView!!.isCursorVisible = true
                nextView!!.requestFocus()

                currentView.isCursorVisible = false
                currentView.isEnabled = false
//                previousView.requestFocus()
            }

            if (currentView.id == R.id.etPassword6 && !currentView.text.isNullOrBlank()) {
                currentView.requestFocus()
                currentView.setSelection(1)
            }
            return false
        }


    }
}
