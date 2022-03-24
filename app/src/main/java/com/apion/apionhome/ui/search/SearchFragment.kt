package com.apion.apionhome.ui.search

import android.os.Build
import android.view.Window
import android.view.WindowManager
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import com.apion.apionhome.R
import com.apion.apionhome.base.BindingFragmentBottomSheet
import com.apion.apionhome.data.model.RangeUI
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

    private fun listener() {
        binding.seekBarPrice.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                viewModel.setPriceIndex(p1)
                binding.textPrice.text =
                    RangeUI.priceRangeUis.values.toMutableList()[viewModel.priceIndex.value ?: 0]
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })
        binding.seekBarArg.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                viewModel.setAcreageIndex(p1)
                binding.textArg.text =
                    RangeUI.acreageRangeUis.values.toMutableList()[viewModel.acreageIndex.value ?: 0]
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })
        binding.seekBarFront.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                viewModel.setFrontWidthIndex(p1)
                binding.textFront.text =
                    RangeUI.frontWidthRangeUis.values.toMutableList()[viewModel.frontWidthIndex.value ?: 0]
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })
        binding.seekBarRoom.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                viewModel.setBedroomsIndex(p1)
                binding.textRoom.text =
                    RangeUI.bedroomUis.values.toMutableList()[viewModel.bedroomIndex.value ?: 0]
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })
    }

    private fun initParam() {
        viewModel.setPrice(0, 100)
        viewModel.setAcreage(0, 1000)
        viewModel.setFrontWidth(0, 100)
    }
}
