package com.apion.apionhome.ui.add_home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apion.apionhome.MyApplication
import com.apion.apionhome.base.RxViewModel
import com.apion.apionhome.data.model.House
import com.apion.apionhome.data.model.RangeUI
import com.apion.apionhome.data.repository.HouseRepository
import com.apion.apionhome.utils.isTitleValid
import com.apion.apionhome.utils.setup
import com.apion.apionhome.utils.transform

class AddHouseViewModel(
    val houseRepository: HouseRepository
) : RxViewModel() {
    val title = MutableLiveData<String>()
    val titleRule = title.transform {
        if (it.isTitleValid) null else 1
    }
    val content = MutableLiveData<String>()

    val contentRule = content.transform {
        if (it.isNotEmpty()) null else "Vui lòng nhập nội dung!"
    }

    val addressRule = MutableLiveData<String?>()
    val price = MutableLiveData<String>()
    val acreage = MutableLiveData<String>()
    val frontWidth = MutableLiveData<String>()
    val bedroom = MutableLiveData<String>()

    private val _houseTypeIndex = MutableLiveData<Int>(1)
    val houseTypeIndex: LiveData<Int>
        get() = _houseTypeIndex

    private val _houseDirectionIndex = MutableLiveData<Int>(0)
    val houseDirectionIndex: LiveData<Int>
        get() = _houseDirectionIndex

    fun createHouse(
        province: String?,
        district: String?,
        ward: String?,
        street: String?,
        detailAddress: String?,
        images: List<String?>
    ) {
        validate()
        if (province == null) {
            addressRule.value = "Vui lòng chọn địa chỉ nhà!"
        } else {
            addressRule.value = null
        }
        val imagesList = mutableListOf<String>()
        images.forEach {
            it?.let {
                imagesList.add(it)
            }
        }
        if(titleRule.value==null&&contentRule.value==null&&addressRule.value==null){
            val house = House(
                houseType = RangeUI.houseTypeRangeUis.keys.toMutableList()[houseTypeIndex.value
                    ?: 1],
                homeDirection = RangeUI.homeDirectionRangeUis.keys.toMutableList()[houseDirectionIndex.value
                    ?: 1],
                title = title.value,
                content = content.value,
                province = province,
                district = district,
                ward = ward,
                street = street,
                address = detailAddress,
                price = price.value?.toLong(),
                frontWidth = frontWidth.value?.toDouble(),
                acreage = acreage.value?.toDouble(),
                bedrooms = bedroom.value?.toInt(),
                userId = MyApplication.sessionUser.value?.id
            )
            houseRepository.createHouse(
                imagesList,
                house
            )  .setup()
                .subscribe(
                    {
                        println(it)
                    }, {
                        it.printStackTrace()
                        error.value = it.message
                    }
                )
        }
    }

    private fun validate(): String? {
        val titleValue = title.value
        if (titleValue.isNullOrBlank()) {
            titleRule.value = 2
        }
        if (content.value.isNullOrBlank()) {
            contentRule.value = "Vui lòng nhập nội dung!"
        } else {
            contentRule.value = null
        }
        if (titleRule.value == null && contentRule.value == null) {
            return ""
        }
        return null
    }

    fun setHouseTypeIndex(index: Int) {
        _houseTypeIndex.value = index
    }

    fun setHouseDirectionIndex(index: Int) {
        _houseDirectionIndex.value = index
    }
}
