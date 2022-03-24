package com.apion.apionhome.ui.search

import android.graphics.Color
import android.widget.SeekBar
import androidx.navigation.fragment.findNavController
import com.apion.apionhome.R
import com.apion.apionhome.base.BindingFragmentBottomSheet
import com.apion.apionhome.data.model.RangeUI
import com.apion.apionhome.databinding.FragmentSearchBinding
import com.apion.apionhome.utils.customview.actionsheet.ActionSheet
import com.apion.apionhome.utils.customview.actionsheet.callback.ActionSheetCallBack
import com.apion.apionhome.utils.showAction
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SearchFragment :
    BindingFragmentBottomSheet<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    override val viewModel by sharedViewModel<SearchViewModel>()
    override var isBackgroundTrans: Boolean = true

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
        binding.textLayoutHouseType.setOnClickListener{
            val data = ArrayList(RangeUI.houseTypeRangeUis.values)
           requireContext().showAction("Chọn loại nhà đất", data,object : ActionSheetCallBack {
               override fun data(data: String, position: Int) {
                   println(position)
                   viewModel.setHouseTypeIndex(position)
                   binding.textLayoutHouseType.setText(data)
               }
           })
        }
        binding.textLayoutDirection.setOnClickListener{
            val data = ArrayList(RangeUI.homeDirectionRangeUis.values)
            requireContext().showAction("Chọn hướng nhà", data,object : ActionSheetCallBack {
                override fun data(data: String, position: Int) {
                    println(position)
                    viewModel.setHouseDirectionIndex(position)
                    binding.textLayoutDirection.setText(data)
                }
            })
        }
        binding.btnSearch.setOnClickListener {
            viewModel.searchHouse()
        }
    }

    private fun initParam() {
        viewModel.setPrice(0, 100)
        viewModel.setAcreage(0, 1000)
        viewModel.setFrontWidth(0, 100)
    }
}
