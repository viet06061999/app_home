package com.apion.apionhome.ui.search

import android.view.WindowManager
import com.apion.apionhome.base.BindingFragmentBottomSheet
import com.apion.apionhome.databinding.BottomsheetPriceAcreaBinding
import com.apion.apionhome.databinding.FragmentSearchBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SearchFragment :
    BindingFragmentBottomSheet<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    override val viewModel by sharedViewModel<SearchViewModel>()

    override fun setupView() {
        binding.lifecycleOwner = this
        initParam()
        listener()
    }

    override fun onResume() {
        super.onResume()
    }

    private fun listener() {

    }

    private fun initParam() {
        viewModel.setPrice(0, 100)
        viewModel.setAcreage(0, 1000)
        viewModel.setFrontWidth(0, 100)
    }
}