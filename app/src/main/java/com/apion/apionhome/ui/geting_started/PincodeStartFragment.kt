package com.apion.apionhome.ui.geting_started

import android.content.Context
import android.content.SharedPreferences
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.apion.apionhome.R
import com.apion.apionhome.base.BindingFragment
import com.apion.apionhome.base.RxViewModel
import com.apion.apionhome.databinding.FragmentStartPincodeBinding
import com.apion.apionhome.viewmodel.UpdatePincodeViewModel
import com.apion.apionhome.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PincodeStartFragment : BindingFragment<FragmentStartPincodeBinding>(FragmentStartPincodeBinding::inflate) {
    override val viewModel by viewModel<UpdatePincodeViewModel>()

    override fun setupView() {
        binding.lifecycleOwner = this
        setupListener()
        setupEdit()
        setupObserver()
    }
    private fun setupEdit() {
        binding.etPassword1.isCursorVisible = true
        binding.etPassword2.isCursorVisible = false
        binding.etPassword3.isCursorVisible = false
        binding.etPassword4.isCursorVisible = false
        println("BBBBBB")

        binding.etPassword1.isEnabled = true
        binding.etPassword2.isEnabled = false
        binding.etPassword3.isEnabled = false
        binding.etPassword4.isEnabled = false


        binding.etPassword1.setOnKeyListener(
            GenericKeyEvent(
                binding.etPassword2,
                binding.etPassword1,
                null
            )
        )
        binding.etPassword2.setOnKeyListener(
            GenericKeyEvent(
                binding.etPassword3,
                binding.etPassword2,
                binding.etPassword1
            )
        )
        binding.etPassword3.setOnKeyListener(
            GenericKeyEvent(
                binding.etPassword4,
                binding.etPassword3,
                binding.etPassword2
            )
        )
        binding.etPassword4.setOnKeyListener(
            GenericKeyEvent(
                null,
                binding.etPassword4,
                binding.etPassword3
            )
        )



    }
    fun setupObserver(){
        viewModel.updatePinCodeDone.observe(this){
            viewModel.updatePinCodeDone.value?.let{
                if(it){
                    val phone    = arguments?.getString("phone", "") ?: ""
                    println("PHONE TO SHAREPHERENCE ${phone}")
                    val mySharedPreferences : SharedPreferences = requireActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
                    mySharedPreferences.edit().putString(phone,"phone")
                    findNavController().navigate(R.id.actionToMain)
                }
            }
        }
    }
    fun setupListener(){
        binding.buttonCreate.setOnClickListener {
            if(binding.etPassword4.text.isNullOrBlank()){
                showDialog2("Thông báo","Vui lòng tạo mã pin!")
            }else{
                var idUser = arguments?.getString("idUser","")?: ""
                viewModel.pincodeNew.value = ""+ binding.etPassword1.text + binding.etPassword2.text + binding.etPassword3.text + binding.etPassword4.text
                viewModel.updatePin(idUser)
            }
        }
        binding.txtCancel.setOnClickListener {
            this.findNavController().navigate(R.id.actionToLogin)
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

            if (event!!.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL && currentView.id != R.id.etPassword1 && currentView.text.isEmpty()) {
                //If current is empty then previous EditText's number will also be deleted
                previousView!!.text = null
                previousView.isCursorVisible = true
                previousView.isEnabled = true
                previousView.requestFocus()

                currentView.isEnabled = false

                return true
            }

            if (event!!.action == KeyEvent.ACTION_UP &&currentView.id != R.id.etPassword4 && !currentView.text.isEmpty()) {
                nextView!!.isEnabled = true
                nextView!!.isCursorVisible = true


                nextView!!.requestFocus()

                currentView.isCursorVisible = true
                currentView.isEnabled = false
                println("AAAAAAAAA")

                return false
//                previousView.requestFocus()
            }


            return false
        }


    }

}