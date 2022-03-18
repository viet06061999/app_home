package com.apion.apionhome.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apion.apionhome.base.RxViewModel
import com.apion.apionhome.data.model.House
import com.apion.apionhome.data.model.dashboard.Dashboard
import com.apion.apionhome.data.model.local.District
import com.apion.apionhome.data.repository.HouseRepository
import com.apion.apionhome.data.repository.UserRepository
import com.apion.apionhome.utils.setup

class DetailViewModel(
    private val userRepository: UserRepository,
    private val houseRepository: HouseRepository
) : RxViewModel() {

    private val _houseDetail = MutableLiveData<House>()

    val houseDetail: LiveData<House>
        get() = _houseDetail

    fun setHouseDetail(house: House) {
        _houseDetail.value = house
    }
}
