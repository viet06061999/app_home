package com.apion.apionhome.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apion.apionhome.base.RxViewModel
import com.apion.apionhome.data.model.House
import com.apion.apionhome.data.repository.HouseRepository
import com.apion.apionhome.utils.setup

class HouseViewModel(val houseRepository: HouseRepository) : RxViewModel() {


    private val _house = MutableLiveData<House>()

    val house: LiveData<House>
        get() = _house

    fun getHouseById(houseId: Int) {
        houseRepository
            .getHouseById(houseId)
            .setup()
            .subscribe(
                {
                    _house.value = it
                }, {
                    it.printStackTrace()
                    error.value = it.message
                }
            )
    }

    fun updateHouse(images: List<String>, house: House) {
        houseRepository
            .updateHouse(images, house)
            .setup()
            .subscribe(
                {
                    _house.value = it
                }, {
                    it.printStackTrace()
                    error.value = it.message
                }
            )
    }
}
