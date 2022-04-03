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
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.HttpException
import java.lang.Exception
import java.util.*

class LoginViewModel(val userRepository: UserRepository) : RxViewModel() {


    // khởi tạo biến _users, khai báo users  và gán _users cho nó
    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>>
        get() = _users

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    val codeSent                  = MutableLiveData<String>()
    val isFirst                   = MutableLiveData<Int?>(0)

    // khởi tạo biến _loginSuccess, khai báo loginSuccess  và gán _loginSuccess cho nó
    val _loginSuccess = MutableLiveData<Pair<Boolean, String?>>()
    val loginSuccess: LiveData<Pair<Boolean, String?>>
        get() = _loginSuccess


    var _isCreateDone = MutableLiveData<Boolean>()

    val isCreateDone: LiveData<Boolean>
        get() = _isCreateDone

    val phone = MutableLiveData<String>()

    val errorText = MutableLiveData<String?>()
    val errorLogin = MutableLiveData<String?>()


    fun setError(error : String?){
        errorText.value = error
    }


    fun getPhoneLogin(): String {
        phone.value?.let {
            var length = it.length
            if(length>1){
                var subSequence = it.subSequence(1,length)
                return "+84"+subSequence
            }
        }


        return ""
    }


    fun login() {
        // ? để check null. khi null thì ko thực hiện scope function apply
        //
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
                        isFirst.value = it.isFirst
                        _loginSuccess.value = true to it.pincode

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

    private fun validatePhone(): String? {
        val phoneValue = phone.value
        when {
            phoneValue.isNullOrBlank() -> errorText.value = "Yêu cầu nhập số điện thoại!"
            errorText.value == null -> {
                _isLoading.value = true
                return phoneValue
            }

        }
        return null
    }

}
