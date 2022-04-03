package com.apion.apionhome.ui.geting_started

import android.app.AlertDialog
import android.os.CountDownTimer
import android.view.KeyEvent
import android.view.KeyEvent.*
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.fragment.findNavController
import com.apion.apionhome.R
import com.apion.apionhome.StartNavigationDirections
import com.apion.apionhome.base.BindingFragment
import com.apion.apionhome.databinding.FragmentVerifyPhoneBinding
import com.apion.apionhome.viewmodel.LoginViewModel
import com.apion.apionhome.viewmodel.UserViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.concurrent.TimeUnit


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
            setVerifyPhone()
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
    fun setVerifyPhone() {
        var mCallbacks = object: PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {

            }
            override fun onVerificationFailed(p0: FirebaseException) {
                println(p0)
                val dialog = AlertDialog.Builder(requireContext())
                dialog.setTitle("Thông báo")
                dialog.setMessage(p0.message)
                dialog.setPositiveButton("Đóng") { _, _ ->
                }
                dialog.show()
            }
            override fun onCodeSent(verfication: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(verfication, p1)
                viewModel.codeSent.value   =  verfication
            }
        }
        var mAuth = FirebaseAuth.getInstance()
//        mAuth.getFirebaseAuthSettings().setAppVerificationDisabledForTesting(true)
        val options = PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber(viewModel.getPhoneFirebase()) // Phone number to verify
            .setTimeout(120, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity()) // Activity (for callback binding)
            .setCallbacks(mCallbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)

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
            )
        )
        binding.etPassword2.setOnKeyListener(
            GenericKeyEvent(
                binding.etPassword3,
                binding.etPassword2,
                binding.etPassword1,
            )
        )
        binding.etPassword3.setOnKeyListener(
            GenericKeyEvent(
                binding.etPassword4,
                binding.etPassword3,
                binding.etPassword2,
            )
        )
        binding.etPassword4.setOnKeyListener(
            GenericKeyEvent(
                binding.etPassword5,
                binding.etPassword4,
                binding.etPassword3,
            )
        )
        binding.etPassword5.setOnKeyListener(
            GenericKeyEvent(
                binding.etPassword6,
                binding.etPassword5,
                binding.etPassword4,
            )
        )
        binding.etPassword6.setOnKeyListener(
            GenericKeyEvent(
                null,
                binding.etPassword6,
                binding.etPassword5,
            )
        )


        binding.btnDoneVerify.setOnClickListener {
            if(binding.etPassword6.text.isNullOrBlank()){
                val dialog = AlertDialog.Builder(requireContext())
                dialog.setTitle("Thông báo")
                dialog.setMessage("Vui lòng nhập mã xác thực!")
                dialog.setPositiveButton("Đóng"){ _, _ -> }
                dialog.show()
            }
            else{
                isValidVerify()
            }
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
    ) : View.OnKeyListener {

        override fun onKey(p0: View?, keyCode: Int, event: KeyEvent?): Boolean {
            if (currentView.id == R.id.etPassword1) {
                currentView.isCursorVisible = true

            }

            if (event?.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL && currentView.id != R.id.etPassword1 && currentView.text.isEmpty()) {
                //If current is empty then previous EditText's number will also be deleted
                previousView!!.text = null
                previousView.isCursorVisible = true
                previousView.isEnabled = true
                previousView.requestFocus()

                currentView.isEnabled = false

                return true
            }

            if (event?.action == KeyEvent.ACTION_UP &&currentView.id != R.id.etPassword6 && !currentView.text.isEmpty()) {
                nextView!!.isEnabled = true
                if(currentView.id == R.id.etPassword5){
                    nextView!!.isCursorVisible = true
                }else{
                    nextView!!.isCursorVisible = true
                }

                nextView!!.requestFocus()

                currentView.isCursorVisible = true
                currentView.isEnabled = false


                return false
//                previousView.requestFocus()
            }


            return false
        }


    }
}
