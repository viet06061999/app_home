package com.apion.apionhome.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apion.apionhome.base.RxViewModel
import com.apion.apionhome.data.model.User
import com.apion.apionhome.data.repository.UserRepository
import com.apion.apionhome.utils.setup

class UserViewModel(val userRepository: UserRepository) : RxViewModel() {

    private val _users = MutableLiveData<List<User>>()

    val users: LiveData<List<User>>
        get() = _users

    private val _user = MutableLiveData<User>()

    val user: LiveData<User>
        get() = _user

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

    fun login(phone: String, pinCode: String) {
        userRepository
            .login(phone, pinCode)
            .setup()
            .subscribe(
                {
                    _user.value = it
                    println("user $it")
                }, {
                    it.printStackTrace()
                    error.value = it.message
                }
            )
    }
}
