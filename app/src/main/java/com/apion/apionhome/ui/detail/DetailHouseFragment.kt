package com.apion.apionhome.ui.detail

import android.app.AlertDialog
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowInsetsController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.apion.apionhome.DetailGraDirections
import com.apion.apionhome.MobileNavigationDirections
import com.apion.apionhome.MyApplication
import com.apion.apionhome.R
import com.apion.apionhome.base.BindingFragment
import com.apion.apionhome.data.model.House
import com.apion.apionhome.databinding.FragmentDetailHouseBinding
import com.apion.apionhome.ui.adapter.HouseAdapter
import com.apion.apionhome.ui.adapter.ImageDetailBannerAdapter
import com.apion.apionhome.ui.person.UserProfileViewModel
import com.apion.apionhome.utils.TabApp
import com.apion.apionhome.utils.toMessage
import com.apion.apionhome.utils.toPhone
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.io.IOException


class DetailHouseFragment :
    BindingFragment<FragmentDetailHouseBinding>(FragmentDetailHouseBinding::inflate) {
    override val viewModel by sharedViewModel<DetailViewModel>()
    val userViewModel by sharedViewModel<UserProfileViewModel>()

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

    private val callback by lazy {
        OnMapReadyCallback { googleMap ->
            Thread {
                try {
                    val address = Geocoder(
                        requireActivity()
                    ).getFromLocationName(args.houseDetail.getDetailAddress(), 1)
                    var latLng = LatLng(20.995195733794585, 105.86181631094217)
                    if (address.isNotEmpty()) {
                        val fist = address.first()
                        if (fist.hasLatitude() && fist.hasLongitude())
                            latLng = LatLng(fist.latitude, fist.longitude)
                    }
                    activity?.runOnUiThread {
                        googleMap.addMarker(MarkerOptions().position(latLng).title("Apion Home"))
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18f))
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }.start()
        }
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
        binding.buttonFollow.setOnClickListener {
            val dialog = AlertDialog.Builder(requireContext())
            dialog.setTitle("Yêu cầu đăng nhập!")
            dialog.setMessage("Vui lòng đăng nhập để sử dụng tính năng này!")
            dialog.setPositiveButton("Đăng nhập") { _, _ ->
                findNavController().navigate(DetailGraDirections.actionDetailToLogin())
                MyApplication.tabToNavigate.value = TabApp.DETAIL_HOUSE
                MyApplication.houseNavigate.value = viewModel.houseDetail.value
            }
            dialog.setNegativeButton(getString(R.string.tittle_button_cancel)) { dialogShow, _ ->
                MyApplication.tabToNavigate.value = null
                MyApplication.houseNavigate.value = null
                dialogShow.dismiss()
            }
            if (MyApplication.sessionUser.value != null) {
                val isFollow =
                    MyApplication.sessionUser.value!!.isFollowing(viewModel.houseDetail.value?.owner?.id.toString())
                if (MyApplication.sessionUser.value!!.id == viewModel.houseDetail.value?.owner?.id) {
                    findNavController().navigate(R.id.actionToAdd)
                } else if (isFollow) {
                    userViewModel.unFollow(
                        MyApplication.sessionUser.value?.id!!,
                        viewModel.houseDetail.value?.owner?.id ?: -1
                    )
                } else {
                    userViewModel.follow(
                        MyApplication.sessionUser.value?.id!!,
                        viewModel.houseDetail.value?.owner?.id ?: -1
                    )
                }
            } else {
                dialog.show()
            }
        }
        binding.buttonCall.setOnClickListener {
            requireContext().toPhone(viewModel.houseDetail.value?.owner?.phone!!)
        }
        binding.buttonMessage.setOnClickListener {
            requireContext().toMessage(viewModel.houseDetail.value?.owner?.phone!!)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    override fun onResume() {
        super.onResume()
        val view = requireActivity().window.decorView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            requireActivity().window.decorView.windowInsetsController?.hide(WindowInsets.Type.statusBars())
            view.windowInsetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        } else {
            view.systemUiVisibility =
                view.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    override fun onStop() {
        super.onStop()
        val view = requireActivity().window.decorView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            view.windowInsetsController?.setSystemBarsAppearance(
                0,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        } else {
            var flags = view.systemUiVisibility
            flags = flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            view.systemUiVisibility = flags
        }
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