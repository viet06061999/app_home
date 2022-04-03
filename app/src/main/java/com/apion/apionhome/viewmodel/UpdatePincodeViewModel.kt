package com.apion.apionhome.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apion.apionhome.base.RxViewModel
import com.apion.apionhome.data.model.User
import com.apion.apionhome.data.model.local.LocationName
import com.apion.apionhome.data.repository.UserRepository
import com.apion.apionhome.utils.setup
import org.json.JSONObject
import retrofit2.HttpException
import java.lang.Exception

class UpdatePincodeViewModel(val userRepository: UserRepository) : RxViewModel() {
    var pincodeNew =  MutableLiveData<String?>()

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

//    fun updatePincode() {
//
//        userRepository
//            .updatePincode(pincodeNew.value!!)
//            .setup()
//            .doOnTerminate {
//                _isLoading.value = false
//
//            }
//            .subscribe(
//                {
//                    _user.value = it
//
//                }, {
//                    if (it is HttpException) {
//                        try {
//                            val jsonError = JSONObject(
//                                String(
//                                    it.response()?.errorBody()?.bytes() ?: byteArrayOf(),
//                                    charset("UTF-8")
//                                )
//                            )
//                            errorRegister.value = jsonError.getString("message")
//                        } catch (e: Exception) {
//                            e.printStackTrace()
//                            errorRegister.value = it.message
//                        }
//                    } else {
//                        errorRegister.value = it.message
//                    }
//                }
//            )
//    }
}