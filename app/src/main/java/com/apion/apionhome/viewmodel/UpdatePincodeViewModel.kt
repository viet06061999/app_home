package com.apion.apionhome.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apion.apionhome.MyApplication
import com.apion.apionhome.base.RxViewModel
import com.apion.apionhome.data.model.User
import com.apion.apionhome.data.model.local.LocationName
import com.apion.apionhome.data.repository.UserRepository
import com.apion.apionhome.utils.setup
import org.json.JSONObject
import retrofit2.HttpException
import java.lang.Exception

class UpdatePincodeViewModel(val userRepository: UserRepository) : RxViewModel() {
    var pincodeNew        =  MutableLiveData<String?>()
    var updatePinCodeDone = MutableLiveData<Boolean?>()

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user



    fun updatePin(id: String) {
            _isLoading.value = true
            userRepository
                .updatePincode(id, pincodeNew.value!!)
                .setup()
                .doOnTerminate {
                    _isLoading.value = false
                }
                .subscribe(
                    {
                        MyApplication.sessionUser.value = it
                        println("myapplication ${ MyApplication.sessionUser.value}")
                        updatePinCodeDone.value = true
                    }, {
                        it.printStackTrace()
                        error.value = it.message
                    }
                )

    }
}