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

    private val _users = MutableLiveData<List<User>>()

    val users: LiveData<List<User>>
        get() = _users

    private val _user = MutableLiveData<User>()

    val user: LiveData<User>
        get() = _user
    private val _loginSuccess = MutableLiveData<Pair<Boolean, String?>>()

    val loginSuccess: LiveData<Pair<Boolean, String?>>
        get() = _loginSuccess

    val phone = MutableLiveData<String>()
    val phoneRule = phone.transform {
        if (it.isPhoneValid) null else "Định dạng không hợp lệ!"
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
                                phoneRule.value = jsonError.getString("message")
                            } catch (e: Exception) {
                                e.printStackTrace()
                                phoneRule.value = it.message
                            }
                        } else {
                            phoneRule.value = it.message
                        }
                    }
                )
        }

    }


    private fun validatePhone(): String? {
        val phoneValue = phone.value
        println("phone $phoneValue")
        when {
            phoneValue.isNullOrBlank() -> phoneRule.value = "Yêu cầu nhập số điện thoại!"
            phoneRule.value == null -> return phoneValue
        }
        return null
    }
}
