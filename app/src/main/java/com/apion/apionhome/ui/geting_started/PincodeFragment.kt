package com.apion.apionhome.ui.geting_started

import com.apion.apionhome.base.BindingFragment
import com.apion.apionhome.base.RxViewModel
import com.apion.apionhome.databinding.FragmentPincodeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class PincodeFragment : BindingFragment<FragmentPincodeBinding>(FragmentPincodeBinding::inflate) {

    override val viewModel by viewModel<RxViewModel>()

    override fun setupView() {
    }

}