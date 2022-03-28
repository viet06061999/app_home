package com.apion.apionhome.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.apion.apionhome.MyApplication
import com.apion.apionhome.base.RxViewModel
import com.apion.apionhome.data.model.User
import com.apion.apionhome.data.model.local.District
import com.apion.apionhome.data.model.local.ILocation
import com.apion.apionhome.data.model.local.LocationName
import com.apion.apionhome.data.model.local.Province
import com.apion.apionhome.data.repository.HouseRepository
import com.apion.apionhome.data.repository.UserRepository
import com.apion.apionhome.data.source.remote.response_entity.UserResponse
import com.apion.apionhome.utils.isPhoneValid
import com.apion.apionhome.utils.setup
import com.apion.apionhome.utils.transform
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.HttpException
import java.lang.Exception
import java.util.*

class UserViewModel(val userRepository: UserRepository,private val houseRepository: HouseRepository) : RxViewModel() {


    // khởi tạo biến _users, khai báo users  và gán _users cho nó
    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>>
    get() = _users


    // khởi tạo biến _user, khai báo user  và gán _users cho nó


    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    val nameRegister = MutableLiveData<String>()

    val phoneRegister = MutableLiveData<String>()

    // khởi tạo biến _loginSuccess, khai báo loginSuccess  và gán _loginSuccess cho nó
    private val _loginSuccess = MutableLiveData<Pair<Boolean, String?>>()
    val loginSuccess: LiveData<Pair<Boolean, String?>>
        get() = _loginSuccess

    private val _district = MutableLiveData<District?>()
    val district: LiveData<District?>
        get() = _district

    private val _locations = MutableLiveData<List<ILocation>>()
    val locations: LiveData<List<ILocation>>
        get() = _locations

    private val _province = MutableLiveData<Province?>()
    val province: LiveData<Province?>
        get() = _province


    private val _ward = MutableLiveData<LocationName?>()
    val ward: LiveData<LocationName?>
        get() = _ward

    private val _street = MutableLiveData<LocationName?>()

    val street: LiveData<LocationName?>
        get() = _street


    val phone = MutableLiveData<String>()
//    phone.phone
//    errorText="@{loginVM.errorText}"


    // errorText laf 1 MediatorLivedata
    val errorText = MutableLiveData<String?>()
    val errorName = MutableLiveData<String?>()
    val errorPhone = MutableLiveData<String?>()

    val dateRegister = MutableLiveData<Date>()
    fun setDistrict(district: District?) {
        _district.value = district
        _ward.value = null
        _street.value = null
    }
    fun setWard(locationName: LocationName?) {
        println("PQTHANh")
        println(locationName?.getContent())
        _ward.value = locationName
        _street.value = null

    }



    fun setError(error : String?){
        errorText.value = error
    }
    fun setErrorName(error : String?){
        errorName.value = error
    }
    fun setErrorPhone(error : String?){
        errorPhone.value = error
    }
    fun setDate(date : Date){
        dateRegister.value = date
    }
    fun getAllUser() {
        userRepository
            .getAllUsers()
            .setup()
            .subscribe(
                {
                    _users.value = it
                    println("users $it")
                }, {
                    it.printStackTrace()
                    error.value = it.message
                }
            )
    }
    override fun initData(){
        _province.value = Province(
            id = 2,
            name = "Hà Nội",
            code = "HN",
            districts = mutableListOf()
        )
        _district.value = District(
            id = 25,
            name = "Ba Đình",
            province = Province(
                id = 2,
                name = "Hà Nội",
                code = "HN",
                districts = mutableListOf()
            )
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
                    print(_locations.value)
                    print("PAT")
                }, {
                    it.printStackTrace()
                    error.value = it.message
                }
            )
    }
    fun setProvince(province: Province?) {
        _province.value = province
        if(province?.districts?.isNotEmpty() == true){
            _district.value = province.districts.first()
        }
        println("da gan")
        _ward.value = null
        _street.value = null
    }

    fun login() {
        var name : String = "Viet"
        // ? để check null. khi null thì ko thực hiện scope function apply
        //
        _isLoading.value = true
        validatePhone()?.apply {
            userRepository
                .login(this)
                .setup()
                .doOnTerminate {
                    _isLoading.value = false
                }
                .subscribe(
                    {
                        _user.value = it
                        _loginSuccess.value = true to it.pincode
                        MyApplication.sessionUser.value = it
                    }, {
                        if (it is HttpException) {
                            try {
                                val jsonError = JSONObject(
                                    String(
                                        it.response()?.errorBody()?.bytes() ?: byteArrayOf(),
                                        charset("UTF-8")
                                    )
                                )
                                errorText.value = jsonError.getString("message")
                            } catch (e: Exception) {
                                e.printStackTrace()
                                errorText.value = it.message
                            }
                        } else {
                            errorText.value = it.message
                        }
                    }
                )
        }

    }


    private fun validatePhone(): String? {
        val phoneValue = phone.value
        when {
            phoneValue.isNullOrBlank() -> errorText.value = "Yêu cầu nhập số điện thoại!"
            errorText.value == null -> return phoneValue
        }
        return null
    }

}
