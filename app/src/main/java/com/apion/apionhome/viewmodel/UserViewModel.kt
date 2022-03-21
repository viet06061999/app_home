package com.apion.apionhome.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.apion.apionhome.base.RxViewModel
import com.apion.apionhome.data.model.User
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

class UserViewModel(val userRepository: UserRepository) : RxViewModel() {


    // khởi tạo biến _users, khai báo users  và gán _users cho nó
    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>>
    get() = _users


    // khởi tạo biến _user, khai báo user  và gán _users cho nó
    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    // khởi tạo biến _loginSuccess, khai báo loginSuccess  và gán _loginSuccess cho nó
    private val _loginSuccess = MutableLiveData<Pair<Boolean, String?>>()
    val loginSuccess: LiveData<Pair<Boolean, String?>>
        get() = _loginSuccess



    val phone = MutableLiveData<String>()
//    phone.phone
//    errorText="@{loginVM.errorText}"


    // errorText laf 1 MediatorLivedata
    val errorText = MutableLiveData<String?>()

    fun setError(error : String?){
        errorText.value = error
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

    fun login() {
        var name : String = "Viet"
        // ? để check null. khi null thì ko thực hiện scope function apply
        //
        validatePhone()?.apply {
            userRepository
                .login(this)
                .setup()
                .subscribe(
                    {
                        _user.value = it
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
