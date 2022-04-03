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
    val isLogout = MutableLiveData<Boolean>(false)
    val updatePinCodeDone = MutableLiveData<Boolean>(false)

    val pin1 = MutableLiveData<String?>()
    val pin2 = MutableLiveData<String?>()
    val rulePin1 = pin1.transform {
        if (it.isNullOrBlank()) {
            "Vui lòng nhập mật khẩu"
        } else if (it.length < 4) {
            "Vui lòng nhập 4 số"
        } else {
            null
        }
    }

    val rulePin2 = pin2.transform {
        if (it.isNullOrBlank()) {
            "Vui lòng nhập lại mật khẩu"
        } else if (it.length == pin1.value?.length && it != pin1.value) {
            "Mật khẩu không khớp"
        } else {
            null
        }
    }

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


    fun logout(userId: Int, phone: String) {
        userRepository
            .logout(userId, phone)
            .setup()
            .subscribe(
                {
                    isLogout.value = true
                    MyApplication.sessionUser.value = null
                    MyApplication.houseNavigate.value = null
                    MyApplication.profileUserNavigate.value = null
                    MyApplication.tabToNavigate.value = null
                }, {
                    it.printStackTrace()
                    error.value = it.message
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

    fun updatePin(id: String) {
        if (rulePin1.value == null && rulePin2.value == null) {
            _isLoading.value = true
            userRepository
                .updatePincode(id, pin1.value!!)
                .setup()
                .doOnTerminate {
                    _isLoading.value = false
                }
                .subscribe(
                    {
                        updatePinCodeDone.value = true
                    }, {
                        it.printStackTrace()
                        error.value = it.message
                    }
                )
        }
    }
}