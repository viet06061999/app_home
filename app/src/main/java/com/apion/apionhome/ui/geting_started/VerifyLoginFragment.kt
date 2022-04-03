package com.apion.apionhome.ui.geting_started

import android.app.AlertDialog
import android.os.CountDownTimer
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.apion.apionhome.R
import com.apion.apionhome.base.BindingFragment
import com.apion.apionhome.base.RxViewModel
import com.apion.apionhome.databinding.FragmentVerifyPhoneLoginBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class VerifyLoginFragment : BindingFragment<FragmentVerifyPhoneLoginBinding>(FragmentVerifyPhoneLoginBinding::inflate) {
//    override val viewModel by viewModel<VerifyPhoneViewModel>()
    override val viewModel= RxViewModel()



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
//            setVerifyPhone()
        }
    }
    override fun setupView() {
        val pincode  = arguments?.getString("pincode", "") ?: ""
        timer.start()
        setupTitlePhone()
        setupEdit()
        setupListener()
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
            VerifyPhoneFragment.GenericKeyEvent(
                binding.etPassword2,
                binding.etPassword1,
                null,
            )
        )
        binding.etPassword2.setOnKeyListener(
            VerifyPhoneFragment.GenericKeyEvent(
                binding.etPassword3,
                binding.etPassword2,
                binding.etPassword1,
            )
        )
        binding.etPassword3.setOnKeyListener(
            VerifyPhoneFragment.GenericKeyEvent(
                binding.etPassword4,
                binding.etPassword3,
                binding.etPassword2,
            )
        )
        binding.etPassword4.setOnKeyListener(
            VerifyPhoneFragment.GenericKeyEvent(
                binding.etPassword5,
                binding.etPassword4,
                binding.etPassword3,
            )
        )
        binding.etPassword5.setOnKeyListener(
            VerifyPhoneFragment.GenericKeyEvent(
                binding.etPassword6,
                binding.etPassword5,
                binding.etPassword4,
            )
        )
        binding.etPassword6.setOnKeyListener(
            VerifyPhoneFragment.GenericKeyEvent(
                null,
                binding.etPassword6,
                binding.etPassword5,
            )
        )



    }
    fun setupTitlePhone(){
        val phone    = arguments?.getString("phone", "") ?: ""
        var result = ""
        phone?.let{
            if(it.length >=7){
                result = "+84 " + phone.subSequence(1,4)+" "+phone.subSequence(4,7)+" "+phone.subSequence(7,phone.length)
                binding.textPhone.text = result
            }
        }
    }
    fun setupListener(){
        binding.btnDoneVerify.setOnClickListener {
            if(binding.etPassword6.text.isNullOrBlank()){
                val dialog = AlertDialog.Builder(requireContext())
                dialog.setTitle("Thông báo")
                dialog.setMessage("Vui lòng nhập mã xác thực!")
                dialog.setPositiveButton("Đóng"){ _, _ ->
                    this.findNavController().popBackStack()
                }
                dialog.show()
            }
            else{
                isValidVerify()
            }
        }
        binding.txtAnswer.setOnClickListener {
            val phone    = arguments?.getString("phone", "") ?: ""
            findNavController().navigate(
                R.id.actionToConfirmPhone,
                bundleOf(
                    "phone" to phone,
                )
            )
        }
        binding
    }
    fun isValidVerify() {
        val codeSent = arguments?.getString("codeSent", "") ?: ""
        viewModel._isLoading.value = true
        val pin = binding.etPassword1.text.toString() + binding.etPassword2.text.toString() +
                binding.etPassword3.text.toString() + binding.etPassword4.text.toString() +
                binding.etPassword5.text.toString() + binding.etPassword6.text.toString()
        println("CODE SENT: ")
        println(codeSent)
        codeSent?.let {
            val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(it, pin)
            signIn(credential)
        }

    }

    private fun signIn(credential: PhoneAuthCredential) {
        var mAuth = FirebaseAuth.getInstance()
        val isFirst  = arguments?.getString("isFirst", "") ?: ""

        mAuth.signInWithCredential(credential)
            .addOnCompleteListener { task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    viewModel._isLoading.value = false
                    println("Xác thực thành công")
                    //viewModel.register()
//                    args.isFirst.let{
                    // lần đầu tiên đăng nhập trên ứng dụng, tạo pincode, gửi bundle id
                        if(isFirst==""){
                            timer.cancel()
                            val idUser = arguments?.getString("idUser", "") ?: ""
                            val phone    = arguments?.getString("phone", "") ?: ""

                            println("ID USER: ${idUser}")
                            findNavController().navigate(R.id.actionToVerifyPincodeStartFragment,
                                bundleOf(
                                    "idUser" to idUser,
                                    "phone" to phone
                                ))
                            println("CHUAN BI SANG MAN START PINCODE")


                        }
                        else{
                            // đã từng đăng nhập trên dứng dụng, nhập pincode
                            timer.cancel()
                            findNavController().navigate(R.id.actionToPinCode)
                            println("CHUAN BI SANG MAN PINCODE")

                        }
//                    }

                } else {
                    viewModel._isLoading.value = false
                    val dialog = AlertDialog.Builder(requireContext())
                    dialog.setTitle("Thông báo ")
                    dialog.setMessage("Xác thực thất bại. Vui lòng thử lại!")
                    dialog.setPositiveButton("Đóng") { _, _ ->
                        timer.cancel()
                        this.findNavController().popBackStack()
                    }
                    dialog.show()
                }
            }

    }
}