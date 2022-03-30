package com.apion.apionhome.ui.geting_started

import android.R.attr.phoneNumber
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.view.View
import androidx.navigation.fragment.findNavController
import com.apion.apionhome.R
import com.apion.apionhome.base.BindingFragment
import com.apion.apionhome.databinding.FragmentRegisterBinding
import com.apion.apionhome.utils.isNameValid
import com.apion.apionhome.utils.isPhoneValid
import com.apion.apionhome.viewmodel.UserViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.*
import java.util.concurrent.TimeUnit


class RegisterFragment : BindingFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {
    override val viewModel by sharedViewModel<UserViewModel>()
    val calendar = Calendar.getInstance()
    lateinit var mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    lateinit var mAuth: FirebaseAuth

    override fun setupView() {
        binding.lifecycleOwner = this
        binding.registerVM = viewModel
        println(viewModel.province.value)
        setupListerner()
        setObserve()
        setRadio()
    }

    fun setupListerner() {
        binding.imageBack.setOnClickListener {
            this.findNavController().popBackStack()
        }
        binding.btnRegister.setOnClickListener {
            viewModel.setCreateDone()
        }

        binding.edtDateOfBirth.setOnClickListener {
            setupPickerDialog()
        }
        binding.edtAddress.setOnClickListener {
            this.findNavController().navigate(R.id.actionToSelectLocation)
        }
        binding.icDeleteAddress.setOnClickListener {
//            binding.edtAddress.setText(getString(R.string.text_select_address))
            viewModel.setDistrict(null)
            viewModel.setProvince(null)
        }

    }

    fun setRadio(){
        binding.radioGroup2.setOnCheckedChangeListener { _, id ->
            when (id) {
                R.id.radio_male -> println("Nam")
                R.id.radio_female -> println("Nu")
               }
        }
        binding.radioGroupJob.setOnCheckedChangeListener { _, id ->
            when (id) {
                R.id.radio_otherJob -> println("Viec khac")
                R.id.radio_findJob -> println("Tim viec")
            }
        }
        binding.radioCollege.setOnClickListener({
            binding.radioUniversity.isChecked = false
            binding.radioIntermediate.isChecked = false
            binding.radioHigh.isChecked = false
        })
        binding.radioUniversity.setOnClickListener({
            binding.radioCollege.isChecked = false
            binding.radioIntermediate.isChecked = false
            binding.radioHigh.isChecked = false
        })
        binding.radioIntermediate.setOnClickListener({
            binding.radioCollege.isChecked = false
            binding.radioUniversity.isChecked = false
            binding.radioHigh.isChecked = false
        })
        binding.radioHigh.setOnClickListener({
            binding.radioCollege.isChecked = false
            binding.radioIntermediate.isChecked = false
            binding.radioUniversity.isChecked = false
        })
        binding.btnRegister.setOnClickListener {
            println(viewModel.isCreateDone.value)

            viewModel.setCreateDone()
            println(viewModel.isCreateDone.value)
        }

    }
    private fun verificationCallbacks () {
        mCallbacks = object: PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {

            }
            override fun onVerificationFailed(p0: FirebaseException) {

                println("Sent That Bai")


            }
            override fun onCodeSent(verfication: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(verfication, p1)
                println("Sent Thanh cong")
                viewModel.setCodeSent(verfication)
            }
        }
    }
    private fun verify () {
        verificationCallbacks()

        mAuth = FirebaseAuth.getInstance()
        val options = PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber("+84981813596") // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity()) // Activity (for callback binding)
            .setCallbacks(mCallbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)

    }


    fun setObserve() {

        viewModel.province.observe(this) {
            if (it != null) {
                binding.edtAddress.setText(viewModel.getAddress())
            }else{
                binding.edtAddress.setText(getString(R.string.hint_address_register))
            }
        }
        viewModel.isCreateDone.observe(this){
            if(it){

                verify()
                this.findNavController().navigate(R.id.actionToVerifyPhone)
            }else{
                println("That bai kiem tra lai thong tin")
            }
        }
        viewModel.dateRegister.observe(this, {
            val cal = Calendar.getInstance()
            cal.time = it
            val month = cal.get(Calendar.MONTH) + 1
            binding.edtDateOfBirth.text = "" + cal.get(Calendar.DAY_OF_MONTH) + "/" + month + "/" + cal.get(Calendar.YEAR)
        })

        viewModel.phoneRegister.observe(this, {
            val errorTextPhone = if (it.isPhoneValid) null else "Định dạng không hợp lệ."
            viewModel.setErrorPhone(errorTextPhone)
            if (it.isPhoneValid)
                binding.validatePhone.visibility = View.INVISIBLE
            else
                binding.validatePhone.visibility = View.VISIBLE
                binding.validatePhone.text = "Định dạng không hợp lệ!"
        })
        viewModel.nameRegister.observe(this, {
            val errorTextName =
                if (it.isPhoneValid) null else "Vui lòng không nhập các ký tự đặc biệt."
            viewModel.setErrorName(errorTextName)
            if (it.isNameValid)
                binding.validateName.visibility = View.INVISIBLE
            else
                binding.validateName.visibility = View.VISIBLE
                binding.validateName.text = "Vui lòng không nhập các ký tự đặc biệt."

        })
    }

    fun setupPickerDialog() {
        val dateSetListener = OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            val calendar1 = Calendar.getInstance()
            calendar1.set(year, monthOfYear, dayOfMonth, 23, 59, 59)
            if (calendar1.timeInMillis > calendar.timeInMillis) {
                viewModel.setDate(calendar.time)
            } else {
                viewModel.setDate(calendar1.time)
            }
        }
        var datePickerDialog = DatePickerDialog(
            requireContext(),
            AlertDialog.THEME_HOLO_LIGHT,
            dateSetListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }
}
