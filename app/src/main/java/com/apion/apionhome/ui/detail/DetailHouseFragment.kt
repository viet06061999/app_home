package com.apion.apionhome.ui.detail

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.apion.apionhome.R
import com.apion.apionhome.base.BindingFragment
import com.apion.apionhome.data.model.House
import com.apion.apionhome.databinding.FragmentDetailHouseBinding
import com.apion.apionhome.ui.adapter.HouseAdapter
import com.apion.apionhome.ui.adapter.ImageDetailBannerAdapter
import com.apion.apionhome.ui.home.HomeFragmentDirections
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class DetailHouseFragment :
    BindingFragment<FragmentDetailHouseBinding>(FragmentDetailHouseBinding::inflate) {
    override val viewModel by sharedViewModel<DetailViewModel>()

    private val args by navArgs<DetailActivityArgs>()

    private val sliderHandler = Handler(Looper.getMainLooper())

    private val adapterImage = ImageDetailBannerAdapter(::onItemBannerClick)

    private val adapterRelated = HouseAdapter(::onItemHouseClick)

    private val runnable by lazy {
        Runnable {
            var current = binding.imageSlider.currentItem
            if (adapterImage.itemCount - 1 == current) {
                current = 0
            } else {
                current++
            }
            binding.imageSlider.currentItem = current
        }
    }

    private val callback = OnMapReadyCallback { googleMap ->
        val apionHome = LatLng(20.995195733794585, 105.86181631094217)
        googleMap.addMarker(MarkerOptions().position(apionHome).title("Apion Home"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(apionHome, 18f))
    }

    override fun setupView() {
        viewModel.setHouseDetail(args.houseDetail)
        binding.lifecycleOwner = this
        binding.detailVM = viewModel
        binding.recyclerViewRelated.adapter = adapterRelated
        binding.imageSlider.adapter = adapterImage
        setupBanner()
        binding.buttonBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    override fun onStop() {
        super.onStop()
        sliderHandler.removeCallbacks(runnable)
    }

    private fun setupBanner() {
        binding.imageSlider.adapter = adapterImage
        binding.imageSlider.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                sliderHandler.removeCallbacks(runnable)
                sliderHandler.postDelayed(runnable, 3000)
            }
        })
    }

    private fun onItemHouseClick(house: House) {
        val destination = DetailHouseFragmentDirections.actionToSelf(house)
        findNavController().navigate(destination)
    }

    private fun onItemBannerClick(banner: String) {
        println(banner)
        val destination =
            DetailHouseFragmentDirections.actionDetailToDetailImage(binding.imageSlider.currentItem)
        findNavController().navigate(destination)
    }
}