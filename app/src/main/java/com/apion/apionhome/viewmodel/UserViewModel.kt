package com.apion.apionhome.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.apion.apionhome.MyApplication
import com.apion.apionhome.base.RxViewModel
import com.apion.apionhome.data.model.RangeUI
import com.apion.apionhome.data.model.User
import com.apion.apionhome.data.model.local.District
import com.apion.apionhome.data.model.local.ILocation
import com.apion.apionhome.data.model.local.LocationName
import com.apion.apionhome.data.model.local.Province
import com.apion.apionhome.data.repository.HouseRepository
import com.apion.apionhome.data.repository.UserRepository
import com.apion.apionhome.data.source.remote.response_entity.UserResponse
import com.apion.apionhome.utils.*
import com.apion.apionhome.utils.isNameValid
import com.apion.apionhome.utils.isPhoneValid
import com.apion.apionhome.utils.setup
import com.apion.apionhome.utils.transform
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.HttpException
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.util.*

class UserViewModel(val userRepository: UserRepository,private val houseRepository: HouseRepository) : RxViewModel() {


    // khởi tạo biến _users, khai báo users  và gán _users cho nó
    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>>
    get() = _users


    // khởi tạo biến _user, khai báo user  và gán _users cho nó


    private val _resultAddress = MutableLiveData<String>()
    val resultAddress: LiveData<String>
        get() = _resultAddress


    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private val _userRegister = MutableLiveData<User>()
    val userRegister: LiveData<User>
        get() = _userRegister

    val codeSent                  = MutableLiveData<String>()
    val nameRegister              = MutableLiveData<String>()
    val phoneRegister             = MutableLiveData<String>()
    val phoneReferalRegister      = MutableLiveData<String?>()
    val dobRegister               = MutableLiveData<Calendar?>()
    val sexIndexRegister          = MutableLiveData<Int>()
    val levelIndexRegister        = MutableLiveData<Int?>()
    val jobIndexRegister          = MutableLiveData<Int>()
    val biosRegister              = MutableLiveData<String?>()


    fun setJobIndex(index: Int) {
        jobIndexRegister.value = index
    }
    fun setSexIndex(index: Int) {
        sexIndexRegister.value = index
    }
    fun setLevelIndex(index: Int) {
        levelIndexRegister.value = index
    }
    // khởi tạo biến _loginSuccess, khai báo loginSuccess  và gán _loginSuccess cho nó
    private val _loginSuccess = MutableLiveData<Pair<Boolean, String?>>()
    val loginSuccess: LiveData<Pair<Boolean, String?>>
        get() = _loginSuccess

    private val _registerSuccess = MutableLiveData<Boolean>()
    val registerSuccess: LiveData<Boolean>
        get() = _registerSuccess

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



    var _isCreateDone = MutableLiveData<Boolean>()

    val isCreateDone: LiveData<Boolean>
        get() = _isCreateDone




    val phone = MutableLiveData<String>()
//    phone.phone
//    errorText="@{loginVM.errorText}"


    // errorText laf 1 MediatorLivedata
    val errorText = MutableLiveData<String?>()
    val errorName = MutableLiveData<String?>()
    val errorPhone = MutableLiveData<String?>()

    val resultSentSMS = MutableLiveData<Boolean>()

    val checkExistPhone = MutableLiveData<Boolean?>()
    val textCheckExistPhone = MutableLiveData<String?>()

    val errorRegister = MutableLiveData<String?>()
    val errorLogin = MutableLiveData<String?>()





    fun setError(error : String?){
        errorText.value = error
    }
    fun setErrorName(error : String?){
        errorName.value = error
    }
    fun setErrorPhone(error : String?){
        errorPhone.value = error
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
    fun getAddress(): String? {
        var textAddress  =""
        street.value?.let {
            textAddress += street.value?.prefix+" " +street.value?.name
            if(street.value?.name?.isNotEmpty() == true) textAddress +=", "
        }

        ward.value?.let {
            textAddress +=ward.value?.prefix+" " + ward.value?.name
            if(ward.value?.name?.isNotEmpty() == true) textAddress +=", "
        }
        district.value?.let {
            textAddress += district.value?.name
            if(district.value?.name?.isNotEmpty() == true) textAddress +=", "
        }

        province.value?.let {
            textAddress += province.value?.name
        }

        return textAddress
    }
    fun getDobAPI(): String{

        var result = Calendar.getInstance().time.toString(TimeFormat.TIME_FORMAT_API_1)
        dobRegister.value?.let {
            var date = it.time
            date.let {
                result = it.toString(TimeFormat.TIME_FORMAT_API_1)
            }
        }
        return result
    }
    fun getPhoneFirebase(): String {
        phoneRegister.value?.let {
            var length = it.length
            if(length>1){
            var subSequence = it.subSequence(1,length)
            return "+84"+subSequence
            }
        }


        return ""
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

    fun setCreateDone(){
        _isCreateDone.value = (phoneRegister.value?.isPhoneValid ?: false) && (nameRegister.value?.isNameValid ?: false)
    }
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
    fun setStreet(locationName: LocationName?) {
        _street.value = locationName
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
                        it.following?.forEach {
                            FirebaseMessaging.getInstance().subscribeToTopic("ApionHome${it.beingFollowedId}")
                                .addOnCompleteListener { task ->
                                    if (!task.isSuccessful) {
                                        task.exception?.printStackTrace()
//                                        throw IllegalArgumentException(task.exception)
                                    }
                                }
                        }
                        println("myapplication ${ MyApplication.sessionUser.value}")
                    }, {
                        if (it is HttpException) {
                            try {
                                val jsonError = JSONObject(
                                    String(
                                        it.response()?.errorBody()?.bytes() ?: byteArrayOf(),
                                        charset("UTF-8")
                                    )
                                )
                                errorLogin.value = jsonError.getString("message")
                            } catch (e: Exception) {
                                e.printStackTrace()
                                errorLogin.value = it.message
                            }
                        } else {
                            errorLogin.value = it.message

                        }
                    }
                )
        }

    }
    fun checkExits() {
        // ? để check null. khi null thì ko thực hiện scope function apply
        //
        userRepository
                .login(phoneRegister.value!!)
                .setup()
                .doOnTerminate {
                }
                .subscribe(
                    {
                        _isLoading.value = false
                        textCheckExistPhone.value = "Số điện thoại này đã tồn tại."
                        checkExistPhone.value = true

                    }, {
                        if (it is HttpException) {
                            try {
                                val jsonError = JSONObject(
                                    String(
                                        it.response()?.errorBody()?.bytes() ?: byteArrayOf(),
                                        charset("UTF-8")
                                    )
                                )
//                                errorTextPhone.value =
                                jsonError.getString("message").equals("Số điện thoại này không tồn tại trên hệ thống.")?.let {
                                    checkExistPhone.value = false
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                                _isLoading.value = false
                                textCheckExistPhone.value = "Có lỗi xảy ra."
                                checkExistPhone.value = true

                            }
                        } else {
                            _isLoading.value = false
                            textCheckExistPhone.value = "Có lỗi xảy ra."
                            checkExistPhone.value = true

                        }
                    }
                )


    }
    fun getUser() : User = User(
        name  = nameRegister.value?.toString(),
        phone = phoneRegister.value?.toString(),
        refer = phoneReferalRegister.value?.toString(),
        dateOfBirth = getDobAPI(),
        address = getAddress(),
        sex = RangeUI.sexUis.entries.toList()[sexIndexRegister.value ?: 0].key,
        academicLevel = RangeUI.levelUis.entries.toList()[levelIndexRegister.value ?: 0].key,
        job = RangeUI.jobUis.entries.toList()[jobIndexRegister.value ?: 1].key,
        position = "Expert",
        permission = "Host Side",
        role = "User"

    )
    fun register() {
        val user = getUser()
        println("USER: ")
        println(user.toString())
        userRepository
            .createUser(user)
            .setup()
            .doOnTerminate {
                _isLoading.value = false

            }
            .subscribe(
                {
                    _userRegister.value = it
                    _registerSuccess.value = true

                }, {
                    if (it is HttpException) {
                        try {
                            val jsonError = JSONObject(
                                String(
                                    it.response()?.errorBody()?.bytes() ?: byteArrayOf(),
                                    charset("UTF-8")
                                )
                            )
                            errorRegister.value = jsonError.getString("message")
                        } catch (e: Exception) {
                            e.printStackTrace()
                            errorRegister.value = it.message
                        }
                    } else {
                        errorRegister.value = it.message
                    }
                }
            )
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
