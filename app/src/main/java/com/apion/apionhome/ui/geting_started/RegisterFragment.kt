package com.apion.apionhome.ui.geting_started

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.view.View
import androidx.navigation.fragment.findNavController
import com.apion.apionhome.base.BindingFragment
import com.apion.apionhome.databinding.FragmentRegisterBinding
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
    }
    fun setupListerner(){
        binding.imageBack.setOnClickListener{
            this.findNavController().popBackStack()
        }
        binding.btnRegister.setOnClickListener {

        }

        binding.edtDateOfBirth.setOnClickListener {
            setupPickerDialog()
        }
    }
    fun setObserve(){
        viewModel.dateRegister.observe(this,{
            val cal = Calendar.getInstance()
            cal.time = it
            val month = cal.get(Calendar.MONTH)+1
            binding.edtDateOfBirth.text = ""+ cal.get(Calendar.DAY_OF_MONTH)+"/"+month+"/"+cal.get(Calendar.YEAR)
        })
        viewModel.phoneRegister.observe(this,{
            val errorText = if (it.isPhoneValid) null else "Định dạng không hợp lệ!"
            viewModel.setError(errorText)
            println("phạm Anh Tuấn ")
            if(it.isPhoneValid)
                binding.validatePhone.visibility = View.INVISIBLE
            else
                binding.validatePhone.visibility = View.VISIBLE
                binding.validatePhone.text = "Định dạng không hợp lệ!"

        })
        viewModel.nameRegister.observe(this,{

        })
    }
    fun setupPickerDialog(){
        val dateSetListener = OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            val calendar1 = Calendar.getInstance()
            calendar1.set(year,monthOfYear,dayOfMonth,23,59,59)
            if(calendar1.timeInMillis > calendar.timeInMillis){
                viewModel.setDate(calendar.time)
            }else{
                viewModel.setDate(calendar1.time)
            }
        }
        var datePickerDialog = DatePickerDialog(requireContext(),
            AlertDialog.THEME_HOLO_LIGHT,
            dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))

        datePickerDialog.show()
    }

}