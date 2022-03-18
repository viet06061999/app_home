package com.apion.apionhome.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.apion.apionhome.base.RxViewModel
import com.apion.apionhome.data.model.Range
import com.apion.apionhome.data.model.local.District
import com.apion.apionhome.data.model.local.ILocation
import com.apion.apionhome.data.model.local.LocationName
import com.apion.apionhome.data.model.local.Province
import com.apion.apionhome.data.repository.HouseRepository
import com.apion.apionhome.utils.setup

class SearchViewModel(
    private val houseRepository: HouseRepository
) : RxViewModel() {

    private val _provinces = MutableLiveData<List<Province>>()

    val provinces: LiveData<List<Province>>
        get() = _provinces

    private val _locations = MutableLiveData<List<ILocation>>()

    val locations: LiveData<List<ILocation>>
        get() = _locations

    private val _province = MutableLiveData<Province?>()

    val province: LiveData<Province?>
        get() = _province

    private val _district = MutableLiveData<District?>()

    val district: LiveData<District?>
        get() = _district

    private val _ward = MutableLiveData<LocationName?>()

    val ward: LiveData<LocationName?>
        get() = _ward

    private val _street = MutableLiveData<LocationName?>()

    val street: LiveData<LocationName?>
        get() = _street

    private val _price = MutableLiveData<Range?>()

    val price: LiveData<Range?>
        get() = _price

    private val _acreage = MutableLiveData<Range?>()

    val acreage: LiveData<Range?>
        get() = _acreage

    private val _frontWidth = MutableLiveData<Range?>()

    val frontWidth: LiveData<Range?>
        get() = _frontWidth

    private val empty = emptyList<ILocation>()

    override fun initData() {
        super.initData()
        getAllProvince()
    }

    fun clearSearch() {
        _locations.value = empty
    }

    private fun getAllProvince() {
        houseRepository
            .getAllProvince()
            .setup()
            .subscribe(
                {

                }, {
                    it.printStackTrace()
                    error.value = it.message
                }
            )
    }

    fun searchProvince(query: String) {
        println("province $province")
        houseRepository
            .searchProvince(query)
            .setup()
            .subscribe(
                {
                    _locations.value = it
                }, {
                    it.printStackTrace()
                    error.value = it.message
                }
            )
    }

    fun searchDistrict(query: String) {
        houseRepository
            .searchDistrict(_province.value, query)
            .setup()
            .subscribe(
                {
                    _locations.value = it
                }, {
                    it.printStackTrace()
                    error.value = it.message
                }
            )
    }

    fun searchWard(query: String) {
        houseRepository
            .searchWard(_district.value, query)
            .setup()
            .subscribe(
                {
                    _locations.value = it
                }, {
                    it.printStackTrace()
                    error.value = it.message
                }
            )
    }

    fun searchStreet(query: String) {
        println("province $province")
        houseRepository
            .searchStreet(_district.value, query)
            .setup()
            .subscribe(
                {
                    _locations.value = it
                }, {
                    it.printStackTrace()
                    error.value = it.message
                }
            )
    }

    fun setProvince(province: Province) {
        _province.value = province
        _district.value = null
        _ward.value = null
        _street.value = null
    }

    fun setDistrict(district: District) {
        _province.value = district.province
        _district.value = district
        _ward.value = null
        _street.value = null
    }

    fun setWard(locationName: LocationName) {
        _ward.value = locationName
    }

    fun setStreet(locationName: LocationName) {
        _street.value = locationName
    }

    fun setPrice(min: Int, max: Int) {
        val range = _price.value?.apply {
            this.min = min
            this.max = max
        } ?: Range(min, max, "ty")
        _price.value = range
    }

    fun setAcreage(min: Int, max: Int) {
        val range = _acreage.value?.apply {
            this.min = min
            this.max = max
        } ?: Range(min, max, "m2")
        _acreage.value = range
    }


    fun setFrontWidth(min: Int, max: Int) {
        val range = _frontWidth.value?.apply {
            this.min = min
            this.max = max
        } ?: Range(min, max, "m")
        _frontWidth.value = range
    }
}
