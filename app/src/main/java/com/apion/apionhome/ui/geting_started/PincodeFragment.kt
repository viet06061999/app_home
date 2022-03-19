package com.apion.apionhome.ui.geting_started

import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.apion.apionhome.R
import com.apion.apionhome.base.BindingFragment
import com.apion.apionhome.base.RxViewModel
import com.apion.apionhome.databinding.FragmentPincodeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class PincodeFragment : BindingFragment<FragmentPincodeBinding>(FragmentPincodeBinding::inflate) {

    override val viewModel by viewModel<RxViewModel>()
    private val args: PincodeFragmentArgs by navArgs()

    override fun setupView() {
        binding.lifecycleOwner = this
        setupEdit()
        println(args.pinCode)
    }

    private fun setupEdit() {
        binding.etPassword1.isCursorVisible = false
        binding.etPassword2.isCursorVisible = false
        binding.etPassword3.isCursorVisible = false
        binding.etPassword4.isCursorVisible = false// ẩn con trỏ chuột

        binding.etPassword1.setOnKeyListener(GenericKeyEvent(binding.etPassword1, null))
        binding.etPassword2.setOnKeyListener(
            GenericKeyEvent(
                binding.etPassword2,
                binding.etPassword1
            )
        )
        binding.etPassword3.setOnKeyListener(
            GenericKeyEvent(
                binding.etPassword3,
                binding.etPassword2
            )
        )
        binding.etPassword4.setOnKeyListener(
            GenericKeyEvent(
                binding.etPassword4,
                binding.etPassword3
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
                if (binding.etPassword3.text.toString().length == 1) {
                    isValidPinCode()
                }
            }
        })

        binding.buttonlogin.setOnClickListener {
            isValidPinCode()
        }
    }

    private fun isValidPinCode() {
        val pin =
            binding.etPassword1.text.toString() + binding.etPassword2.text.toString() + binding.etPassword3.text.toString() + binding.etPassword4.text.toString()
        if (args?.pinCode == pin){
            findNavController().navigate(PincodeFragmentDirections.actionToMain())
            requireActivity().finish()
        }
    }

//     fun showDialog() {
//        val inflater = layoutInflater
//        val alertLayout = inflater.inflate(R.layout.layout_custom_dialog,null)
//        val alert = AlertDialog.Builder(this)
//        alert.setView(alertLayout)
//        val dialog = alert.create()
//        var text = alertLayout.findViewById(R.id.content) as TextView?
//        var button = alertLayout.findViewById(R.id.buttonClose) as Button?
//        text?.setText("Pincode không trùng khớp")
//        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        dialog.show()
//        dialog.getWindow()?.setLayout(800, 450)
//
//        button?.setOnClickListener{
//            dialog.dismiss()
//        }
//    }
//
//    override fun showDialog2(){
//        val inflater = layoutInflater
//        val alertLayout = inflater.inflate(R.layout.layout_custom_dialog,null)
//        val alert = AlertDialog.Builder(this)
//        alert.setView(alertLayout)
//        val dialog = alert.create()
//        var text = alertLayout.findViewById(R.id.content) as TextView?
//        var button = alertLayout.findViewById(R.id.buttonClose) as Button?
//        text?.setText("Vui lòng nhập đầy đủ pincode!")
//        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        dialog.show()
//        dialog.getWindow()?.setLayout(800, 450)
//
//        button?.setOnClickListener{
//            dialog.dismiss()
//        }
//    }

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