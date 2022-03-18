package com.apion.apionhome.ui.home

import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.apion.apionhome.R
import com.apion.apionhome.base.BindingFragment
import com.apion.apionhome.base.BindingFragmentPickImage
import com.apion.apionhome.data.model.community.Community
import com.apion.apionhome.data.model.local.District
import com.apion.apionhome.data.model.local.LocationName
import com.apion.apionhome.data.model.local.Province
import com.apion.apionhome.databinding.FragmentHomeBinding
import com.apion.apionhome.databinding.FragmentHomeTmpBinding
import com.apion.apionhome.ui.search.SearchLocationBottomSheet
import com.apion.apionhome.ui.search.SearchViewModel
import com.apion.apionhome.viewmodel.CommunityViewModel
import com.apion.apionhome.viewmodel.HouseViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragmentTmp :
    BindingFragmentPickImage<FragmentHomeTmpBinding>(FragmentHomeTmpBinding::inflate) {

    override val viewModel by sharedViewModel<HomeViewModel>()

    private val houseViewModel by viewModel<HouseViewModel>()

    private val communityViewModel by viewModel<CommunityViewModel>()

    private val searchViewModel by sharedViewModel<SearchViewModel>()

    private val pass = CharArray(4)

    override fun setupView() {
        binding.lifecycleOwner = this
        binding.searchVM = searchViewModel
        binding.homeVM = viewModel
        setupListener()
    }

    override fun onImagesSelect(images: List<String>) {
//        houseViewModel.house.value?.let {
//            houseViewModel.updateHouse(images, it)
//        }
        communityViewModel.createCommunity(
            images.firstOrNull(),
            images.getOrNull(1),
            Community(
                name = "Yên Thế Hạ",
                district = "Yên Thế",
                shortDesc = "Cộng đồng anh em bất động sản Yên Thế"
            )
        )
    }

    override fun onResume() {
        super.onResume()
        binding.apply {
            textShowPass.setOnClickListener {
                viewModel.setShowPass()
            }
            textInput1.apply {
                requestFocus()
                setOnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        textInput1.setSelection(textInput1.text.length)
                    }
                }
                doOnTextChanged { _, _, _, _ ->
                    if (textInput1.text?.length == 1 && textInput1.text[0] != pass[0]) {
                        println("text ${textInput1.text?.get(0)}")
                        pass[0] = textInput1.text?.get(0) ?: '*'
                        textInput1.setText(pass[0].toString())
                        textInput2.requestFocus()
                    } else if (textInput1.text?.length == 0) {
                        pass[0] = '*'
                    }
                }
            }

            textInput2.apply {
                setOnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        textInput2.setSelection(textInput2.text.length)
                    }
                }
                doOnTextChanged { _, _, _, _ ->
                    if (textInput2.text?.length == 1 && textInput2.text[0] != pass[1]) {
                        pass[1] = textInput2.text?.get(0) ?: '*'
                        textInput2.setText(pass[1].toString())
                        textInput3.requestFocus()
                    } else if (textInput2.text?.length == 0) {
                        pass[1] = '*'
                        textInput1.requestFocus()
                    }
                }
            }
            textInput3.apply {
                setOnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        textInput3.setSelection(textInput3.text.length)
                    }
                }
                doOnTextChanged { _, _, _, _ ->
                    if (textInput3.text?.length == 1 && textInput3.text[0] != pass[2]) {
                        pass[2] = textInput3.text?.get(0) ?: '*'
                        textInput3.setText(pass[2].toString())
                        textInputDone.requestFocus()
                    } else if (textInput3.text?.length == 0) {
                        pass[2] = '*'
                        textInput2.requestFocus()
                    }
                }
            }
            textInputDone.apply {
                setOnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        println("lenght ${textInputDone.text.length}")
                        textInputDone.setSelection(textInputDone.text.length)
                    }
                }
                doOnTextChanged { _, _, _, _ ->
                    if (textInputDone.text?.length == 1 && textInputDone.text[0] != pass[3]) {
                        pass[3] = textInputDone.text?.get(0) ?: '*'
                        textInputDone.setText(pass[3].toString())
                        textInputDone.onEditorAction(EditorInfo.IME_ACTION_DONE)
                        println(pass)
                    } else if (textInputDone.text?.length == 0) {
                        pass[3] = '*'
                        textInput3.requestFocus()
                    }
                    textInputDone.setSelection(textInputDone.text.length)
                }
            }
        }
    }

    private fun setupListener() {
        binding.buttonAddImage.setOnClickListener {
            pickImageSafety()
        }

        binding.buttonDetailHouseImage.setOnClickListener {
            println("click")
            findNavController().navigate(R.id.actionToDetail)
        }

        binding.buttonSearchProvince.setOnClickListener {
            findNavController().navigate(R.id.actionToSearchProvinceFragment)
        }

        binding.buttonSearchDistrict.setOnClickListener {
            findNavController().navigate(R.id.actionToSearchDistrictFragment)
        }

        binding.buttonSearchWard.setOnClickListener {
            if (searchViewModel.district.value != null) {
                findNavController().navigate(R.id.actionToSearchWardFragment)
            } else {
                showToast("Vui lòng chọn quận, huyện trước")
            }
        }

        binding.buttonSearchStreet.setOnClickListener {
            if (searchViewModel.district.value != null) {
                findNavController().navigate(R.id.actionToSearchStreetFragment)
            } else {
                showToast("Vui lòng chọn quận, huyện trước")
            }
        }
    }
}
