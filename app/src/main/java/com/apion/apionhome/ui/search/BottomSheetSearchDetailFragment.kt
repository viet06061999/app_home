package com.apion.apionhome.ui.search

import androidx.navigation.fragment.findNavController
import com.apion.apionhome.R
import com.apion.apionhome.base.BindingFragmentBottomSheet
import com.apion.apionhome.base.RxViewModel
import com.apion.apionhome.databinding.BottomsheetSearchDetailBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class BottomSheetSearchDetailFragment : BindingFragmentBottomSheet<BottomsheetSearchDetailBinding>(BottomsheetSearchDetailBinding::inflate){
    override val viewModel by sharedViewModel<SearchViewModel>()

    override fun setupView() {
        binding.lifecycleOwner = this
        binding.searchVM = viewModel
        listener()
    }


    fun listener(){
        binding.edtWard.setOnClickListener {
            this.findNavController().navigate(R.id.actionToSearchWardFragment)
        }
        binding.edtStreet.setOnClickListener {
            this.findNavController().navigate(R.id.actionToSearchStreetFragment)
        }
    }
}