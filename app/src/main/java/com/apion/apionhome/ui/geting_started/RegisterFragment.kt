package com.apion.apionhome.ui.geting_started

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.view.View
import android.widget.RadioButton
import androidx.navigation.fragment.findNavController
import com.apion.apionhome.R
import com.apion.apionhome.base.BindingFragment
import com.apion.apionhome.databinding.FragmentRegisterBinding
import com.apion.apionhome.utils.isNameValid
import com.apion.apionhome.utils.isPhoneValid
import com.apion.apionhome.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class RegisterFragment : BindingFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {
    override val viewModel by viewModel<UserViewModel>()
    val calendar = Calendar.getInstance()

    override fun setupView() {
        binding.lifecycleOwner = this
        binding.registerVM = viewModel
        setupListerner()
        setObserve()
        setRadio()
    }

    fun setupListerner() {
        binding.imageBack.setOnClickListener {
            this.findNavController().popBackStack()
        }
        binding.btnRegister.setOnClickListener {

        }

        binding.edtDateOfBirth.setOnClickListener {
            setupPickerDialog()
        }
        binding.edtAddress.setOnClickListener {
            this.findNavController().navigate(R.id.actionToSelectLocation)
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

    }
    fun setObserve() {
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
