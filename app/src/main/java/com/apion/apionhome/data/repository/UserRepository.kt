package com.apion.apionhome.data.repository

import com.apion.apionhome.data.model.House
import com.apion.apionhome.data.model.User
import com.apion.apionhome.data.model.UserFollowed
import io.reactivex.rxjava3.core.Maybe

interface UserRepository {

    fun getAllUsers(): Maybe<List<User>>
    fun getUserById(id: Int): Maybe<User>

    fun createUser(user: User): Maybe<User>

    fun updateUser(user: User): Maybe<User>

    fun login(phone: String): Maybe<User>
    fun follow(followerId: Int, beingFollowedId: Int): Maybe<UserFollowed>
    fun unFollow(followerId: Int, beingFollowedId: Int): Maybe<UserFollowed>
    fun uploadAvatar(id: Int, image: String): Maybe<User>
    fun logout(id: Int, phone: String): Maybe<User>
    fun updatePincode(id: String, pin: String): Maybe<User>
}
