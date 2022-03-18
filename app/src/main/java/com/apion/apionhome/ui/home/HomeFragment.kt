package com.apion.apionhome.ui.home

import android.os.Handler
import android.os.Looper
import android.view.ViewTreeObserver
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.apion.apionhome.R
import com.apion.apionhome.base.BindingFragment
import com.apion.apionhome.data.model.House
import com.apion.apionhome.data.model.User
import com.apion.apionhome.data.model.dashboard.Banner
import com.apion.apionhome.databinding.FragmentHomeBinding
import com.apion.apionhome.ui.adapter.HouseAdapter
import com.apion.apionhome.ui.adapter.ImageSliderAdapter
import com.apion.apionhome.ui.adapter.UserOnlineAdapter
import com.apion.apionhome.ui.search.SearchViewModel
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.lang.IllegalStateException


class HomeFragment :
    BindingFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override val viewModel by sharedViewModel<HomeViewModel>()

    private val searchViewModel by sharedViewModel<SearchViewModel>()

    private val sliderHandler = Handler(Looper.getMainLooper())

    private val adapterImage = ImageSliderAdapter(::onItemBannerClick)

    private val adapterFeature = HouseAdapter(::onItemHouseClick)

    private val adapterHanoi = HouseAdapter(::onItemHouseClick)

    private val adapterSaiGon = HouseAdapter(::onItemHouseClick)

    private val adapterUserOnline = UserOnlineAdapter(::onItemUserOnlineClick, ::onChatNowClick)

    private var isCheck = false

    private val observerVisible =
        ViewTreeObserver.OnGlobalLayoutListener {
            binding.swipeLayout.isEnabled = binding.layoutHeader.root.isVisible
        }

    private val runnable by lazy {
        Runnable {
            var current = binding.layoutBanner.imageSlider.currentItem
            if (adapterImage.itemCount - 1 == current) {
                current = 0
            } else {
                current++
            }
            binding.layoutBanner.imageSlider.currentItem = current
        }
    }

    override fun setupView() {
        binding.lifecycleOwner = this
        binding.layoutFeature.recyclerViewFeature.adapter = adapterFeature
        binding.layoutHanoi.recyclerViewFeature.adapter = adapterHanoi
        binding.layoutSaigon.recyclerViewFeature.adapter = adapterSaiGon
        binding.pagerUserOnline.adapter = adapterUserOnline
        setupBanner()
        setupListener()
        setupRefresh()
        setupSearchView()
    }

    override fun onConnectionAvailable() {
        binding.searchVM = searchViewModel
        binding.homeVM = viewModel
        viewModel.initData()
    }

    private fun setupListener() {
        binding.layoutHeader.layoutPrice.setOnClickListener {
            findNavController().navigate(R.id.actionToBottomSheetPriceAcrea)
        }

        binding.layoutHeader.editTextCity.setOnClickListener {
            findNavController().navigate(R.id.actionToSearchProvinceFragment)
        }

        binding.layoutHeader.editTextDistrict.setOnClickListener {
            findNavController().navigate(R.id.actionToSearchDistrictFragment)
        }

        binding.layoutHeader.editTextWard.setOnClickListener {
            if (searchViewModel.district.value != null) {
                findNavController().navigate(R.id.actionToSearchWardFragment)
            } else {
                showToast(getString(R.string.error_select_ward))
            }
        }

        binding.layoutHeader.editTextStreet.setOnClickListener {
            if (searchViewModel.district.value != null) {
                findNavController().navigate(R.id.actionToSearchStreetFragment)
            } else {
                showToast(getString(R.string.error_select_ward))
            }
        }
    }

    override fun onStop() {
        super.onStop()
        binding.layoutHeader.root.viewTreeObserver.removeOnGlobalLayoutListener(observerVisible)
        sliderHandler.removeCallbacks(runnable)
    }

    private fun setupRefresh() {
        binding.swipeLayout.apply {
            binding.layoutHeader.root.viewTreeObserver.addOnGlobalLayoutListener(observerVisible)
            setOnRefreshListener {
                viewModel.getDashboard() {
                    binding.swipeLayout.isRefreshing = false
                    binding.layoutBanner.imageSlider.currentItem = 0
                }
            }
        }
    }

    private fun setupBanner() {
        isCheck = true
        binding.layoutBanner.imageSlider.adapter = adapterImage
        TabLayoutMediator(binding.tabLayout, binding.layoutBanner.imageSlider) { _, _ ->
        }.attach()
        binding.layoutBanner.imageSlider.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                sliderHandler.removeCallbacks(runnable)
                sliderHandler.postDelayed(runnable, 3000)
            }
        })
    }

    private fun setupSearchView() {
        binding.search.clearFocus()
    }

    private fun onItemBannerClick(banner: Banner) {
        println(banner.link)
    }

    private fun onItemHouseClick(house: House) {
        println(house)
        val destination = HomeFragmentDirections.actionToDetail(house)
        findNavController().navigate(destination)
    }


    private fun onChatNowClick(user: User) {
        println(user)
    }

    private fun onItemUserOnlineClick(user: User) {
        println(user)
    }
}
