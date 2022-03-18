package com.apion.apionhome.ui.search

import android.app.Dialog
import android.os.Bundle
import android.view.View
import com.apion.apionhome.R
import com.apion.apionhome.base.BindingFragmentBottomSheet
import com.apion.apionhome.data.model.Range
import com.apion.apionhome.databinding.BottomsheetPriceAcreaBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class BottomSheetPriceAcrea :
    BindingFragmentBottomSheet<BottomsheetPriceAcreaBinding>(BottomsheetPriceAcreaBinding::inflate) {

    override val viewModel by sharedViewModel<SearchViewModel>()

    override fun setupView() {
        binding.lifecycleOwner = this
        binding.searchVM = viewModel
        initParam()
        listener()
    }

    private fun listener() {
        binding.rangeSliderPrice.addOnChangeListener { _, _, _ ->
            val values = binding.rangeSliderPrice.values
            viewModel.setPrice(values.get(0).toInt(), values.get(1).toInt())
        }

        binding.rangeSliderAcreage.addOnChangeListener { _, _, _ ->
            val values = binding.rangeSliderAcreage.values
            viewModel.setAcreage(values.get(0).toInt(), values.get(1).toInt())
        }

        binding.rangeSliderFrontWidth.addOnChangeListener { _, _, _ ->
            val values = binding.rangeSliderFrontWidth.values
            viewModel.setFrontWidth(values.get(0).toInt(), values.get(1).toInt())
        }

        binding.buttonDone.setOnClickListener {
            dismiss()
        }
    }

    private fun initParam() {
        viewModel.setPrice(0, 100)
        viewModel.setAcreage(0, 1000)
        viewModel.setFrontWidth(0, 100)
    }
}