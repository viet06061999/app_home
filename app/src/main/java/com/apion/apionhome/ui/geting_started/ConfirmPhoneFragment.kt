package com.apion.apionhome.ui.geting_started

import androidx.navigation.fragment.findNavController
import com.apion.apionhome.R
import com.apion.apionhome.base.BindingFragment
import com.apion.apionhome.base.RxViewModel
import com.apion.apionhome.databinding.FragmentConfirmPhoneBinding

class ConfirmPhoneFragment :  BindingFragment<FragmentConfirmPhoneBinding>(FragmentConfirmPhoneBinding::inflate){
    override val viewModel = RxViewModel()

    override fun setupView() {
        setupListener()
        setupTextPhone()
    }
    fun setupTextPhone(){
        val phone    = arguments?.getString("phone", "") ?: ""
        var result = ""
        phone?.let{
            if(it.length >=7){
                result = "" + phone.subSequence(1,4)+" "+phone.subSequence(4,7)+" "+phone.subSequence(7,phone.length)
                binding.txtPhone2.text = result
            }
        }
    }
    fun setupListener(){
        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnDoneConfirm.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}