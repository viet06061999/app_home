package com.apion.apionhome.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.apion.apionhome.R
import com.apion.apionhome.base.BindingFragmentBottomSheet
import com.apion.apionhome.databinding.FragmentSelectLocationBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SelectLocationFragment :
    BindingFragmentBottomSheet<FragmentSelectLocationBinding>(FragmentSelectLocationBinding::inflate) {

    override val viewModel by sharedViewModel<SearchViewModel>()
    override var isBackgroundTrans: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("lai vao set up view")
        viewModel.initData()
    }

    override fun setupView() {
        binding.lifecycleOwner = this
        binding.searchVM = viewModel
        initParam()
        listener()
    }

    private fun listener() {
        binding.edtProvince.setOnClickListener {
            findNavController().navigate(R.id.actionToSearchProvinceFragment)
        }
        binding.edtDistrict.setOnClickListener {
            findNavController().navigate(R.id.actionToSearchDistrictFragment)
        }
        binding.edtStreet.setOnClickListener {
            findNavController().navigate(R.id.actionToSearchStreetFragment)
        }
        binding.edtWard.setOnClickListener {
            findNavController().navigate(R.id.actionToSearchWardFragment)
        }
        binding.btnSearch.setOnClickListener {
            viewModel.searchHouse()
        }
    }

    private fun initParam() {
    }
}
