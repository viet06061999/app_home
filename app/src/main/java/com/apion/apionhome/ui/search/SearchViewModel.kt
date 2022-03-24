package com.apion.apionhome.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apion.apionhome.base.RxViewModel
import com.apion.apionhome.data.model.local.District
import com.apion.apionhome.data.model.local.ILocation
import com.apion.apionhome.data.model.House
import com.apion.apionhome.data.model.Range
import com.apion.apionhome.data.model.RangeUI
import com.apion.apionhome.data.model.SearchParam
import com.apion.apionhome.data.model.local.LocationName
import com.apion.apionhome.data.model.local.Province
import com.apion.apionhome.data.repository.HouseRepository
import com.apion.apionhome.utils.setup

class SearchViewModel(private val houseRepository : HouseRepository) : RxViewModel() {

    val title = MutableLiveData<String>()

    private val _provinces = MutableLiveData<List<Province>>()

    val provinces: LiveData<List<Province>>
        get() = _provinces

    private val _district = MutableLiveData<District?>()

    val district: LiveData<District?>
        get() = _district

    private val _province = MutableLiveData<Province?>(
        Province(
            id = 2,
            name = "Hà Nội",
            code = "HN",
            districts = mutableListOf()
        )
    )
    val province: LiveData<Province?>
        get() = _province
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

    private val _locations = MutableLiveData<List<ILocation>>()
    val locations: LiveData<List<ILocation>>
        get() = _locations
    private val _frontWidth = MutableLiveData<Range?>()

    val frontWidth: LiveData<Range?>
        get() = _frontWidth

    private val _texts = MutableLiveData<String>()
    val texts: LiveData<String>
        get() = _texts

    private val empty = emptyList<ILocation>()

    private val _priceIndex = MutableLiveData<Int>(0)

    val priceIndex: LiveData<Int>
        get() = _priceIndex

    private val _acreageIndex = MutableLiveData<Int>(0)

    val acreageIndex: LiveData<Int>
        get() = _acreageIndex

    private val _frontWidthIndex = MutableLiveData<Int>(0)

    val frontWidthIndex: LiveData<Int>
        get() = _frontWidthIndex

    private val _bedroomIndex = MutableLiveData<Int>(0)

    val bedroomIndex: LiveData<Int>
        get() = _bedroomIndex

    private val _houseTypeIndex = MutableLiveData<Int>(0)

    val houseTypeIndex: LiveData<Int>
        get() = _houseTypeIndex

    private val _houseDirectionIndex = MutableLiveData<Int>(0)

    val houseDirectionIndex: LiveData<Int>
        get() = _houseDirectionIndex

    private val _housesSearch = MutableLiveData<List<House>>()

    val housesSearch: LiveData<List<House>>
        get() = _housesSearch

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

//    fun searchProvince(query: String) {
//        println("province $province")
//        houseRepository
//            .searchProvince(query)
//            .setup()
//            .subscribe(
//                {
//                    _locations.value = it
//                }, {
//                    it.printStackTrace()
//                    error.value = it.message
//                }
//            )
//    }

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

    fun searchProvince(query: String) {
        houseRepository
            .searchProvince(query)
            .setup()
            .subscribe(
                {
                    _locations.value = it
                },
                {
                    it.printStackTrace()
                    error.value = it.message
                })

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

    fun searchHouse() {
        val param = SearchParam(
            title.value,
            province.value?.name,
            district.value?.name,
            ward.value?.name,
            street.value?.name,
            RangeUI.houseTypeRangeUis.entries.toList()[houseDirectionIndex.value ?: 0].key,
            RangeUI.homeDirectionRangeUis.entries.toList()[houseDirectionIndex.value ?: 0].key,
            RangeUI.priceRangeUis.entries.toList()[priceIndex.value ?: 0].key,
            RangeUI.acreageRangeUis.entries.toList()[acreageIndex.value ?: 0].key,
            RangeUI.frontWidthRangeUis.entries.toList()[frontWidthIndex.value ?: 0].key,
            RangeUI.bedroomUis.entries.toList()[bedroomIndex.value ?: 0].key,
        )
        println(param)
        houseRepository
            .getSearchHouse(param)
            .setup()
            .subscribe(
                {
                    println("search house -----------------")
                    println(it)
                    _housesSearch.value = it
                }, {
                    it.printStackTrace()
                    error.value = it.message
                }
            )
    }


        fun setProvince(province: Province) {
            _province.value = province
            _district.value = province.districts.first()
//        _district.value = null
//        _ward.value = null
//        _street.value = null
        }

    fun setDistrict(district: District) {
        _district.value = district
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

        fun setFrontWidthIndex(index: Int) {
            _frontWidthIndex.value = index
        }

        fun setPriceIndex(index: Int) {
            _priceIndex.value = index
        }

        fun setAcreageIndex(index: Int) {
            _acreageIndex.value = index
        }

        fun setHouseTypeIndex(index: Int) {
            _houseTypeIndex.value = index
        }

        fun setHouseDirectionIndex(index: Int) {
            _houseDirectionIndex.value = index
        }

        fun setBedroomsIndex(index: Int) {
            _bedroomIndex.value = index
        }

}
