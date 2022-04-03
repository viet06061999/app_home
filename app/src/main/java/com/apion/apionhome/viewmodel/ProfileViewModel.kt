package com.apion.apionhome.viewmodel

import androidx.lifecycle.MutableLiveData
import com.apion.apionhome.MyApplication
import com.apion.apionhome.base.RxViewModel
import com.apion.apionhome.data.model.User
import com.apion.apionhome.data.repository.UserRepository
import com.apion.apionhome.utils.isEmailValid
import com.apion.apionhome.utils.setup
import com.apion.apionhome.utils.transform
import com.google.firebase.messaging.FirebaseMessaging
import org.json.JSONObject
import retrofit2.HttpException
import java.lang.Exception

class ProfileViewModel(val userRepository: UserRepository) : RxViewModel() {
    val email = MutableLiveData<String?>()
    val fbId = MutableLiveData<String?>()
    val updateDone = MutableLiveData<Boolean>(false)

    val emailRule = email.transform {
        if (it?.isEmailValid == true) null else "Định dạng không hợp lệ!"
    }

    fun fetchUser(userId: Int) {
        userRepository
            .getUserById(userId)
            .setup()
            .subscribe(
                {
                    MyApplication.sessionUser.value = it
                }, {
                    it.printStackTrace()
                    error.value = it.message
                }
            )
    }

    fun uploadAvatar(userId: Int, avatar: String) {
        _isLoading.value = true
        userRepository
            .uploadAvatar(userId, avatar)
            .setup()
            .doOnTerminate {
                _isLoading.value = false
            }
            .subscribe(
                {
                    fetchUser(userId)
                    _isLoading.value = false
                }, {
                    it.printStackTrace()
                    error.value = it.message
                    _isLoading.value = false
                }
            )
    }

    fun updateUser(user: User?) {
        if (emailRule.value == null) {
            user?.let {
                _isLoading.value = true
                val userTmp = user.copy()
                userTmp.email = email.value
                userTmp.facebook_id = fbId.value
                userRepository
                    .updateUser(userTmp)
                    .setup()
                    .doOnTerminate {
                        _isLoading.value = false
                    }
                    .subscribe(
                        {
                            updateDone.value = true
                            _isLoading.value = false
                            fetchUser(user.id ?: -1)
                        }, {
                            it.printStackTrace()
                            error.value = it.message
                            _isLoading.value = false
                        }
                    )
            }
        }
    }
}