package com.apion.apionhome.ui.search

import androidx.navigation.fragment.findNavController
import com.apion.apionhome.R
import com.apion.apionhome.base.BindingFragment
import com.apion.apionhome.base.BindingFragmentBottomSheet
import com.apion.apionhome.base.RxViewModel
import com.apion.apionhome.databinding.FragmentSearchDetailBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SearchDetailFragment : BindingFragment<FragmentSearchDetailBinding>(FragmentSearchDetailBinding::inflate){
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
//        binding.edtStreet.setOnClickListener {
//            this.findNavController().navigate(R.id.actionToSearchStreetFragment)
//        }
        binding.iconBack.setOnClickListener {
            this.findNavController().popBackStack()
        }
        binding.edtStreet.setOnClickListener {
            this.findNavController().navigate(R.id.actionToSearchStreetFragment)
        }
    }
}
//class BottomSheetSearchDetailFragment : BindingFragment<>