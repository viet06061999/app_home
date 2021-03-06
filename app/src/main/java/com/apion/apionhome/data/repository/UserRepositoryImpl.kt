package com.apion.apionhome.data.repository

import com.apion.apionhome.data.model.User
import com.apion.apionhome.data.model.UserFollowed
import com.apion.apionhome.data.source.UserDatasource
import io.reactivex.rxjava3.core.Maybe
import java.lang.Exception

class UserRepositoryImpl(private val remote: UserDatasource.Remote) : UserRepository {

    override fun getAllUsers(): Maybe<List<User>> = remote.getAllUsers()

    override fun getUserById(id: Int): Maybe<User> = remote.getUserById(id)

    override fun createUser(user: User): Maybe<User> {
        return try {
            remote.createUser(user)
        } catch (exception: Exception) {
            Maybe.error(exception)
        }
    }

    override fun updateUser(user: User): Maybe<User> {
        return try {
            remote.updateUser(user)
        } catch (exception: Exception) {
            Maybe.error(exception)
        }
    }

    override fun login(phone: String): Maybe<User> {
        return try {
            remote.login(phone)
        } catch (exception: Exception) {
            exception.printStackTrace()
            Maybe.error(exception)
        }
    }

    override fun follow(followerId: Int, beingFollowedId: Int): Maybe<UserFollowed> {
        return try {
            remote.follow(followerId, beingFollowedId)
        } catch (exception: Exception) {
            exception.printStackTrace()
            Maybe.error(exception)
        }
    }

    override fun unFollow(followerId: Int, beingFollowedId: Int): Maybe<UserFollowed> {
        return try {
            remote.unFollow(followerId, beingFollowedId)
        } catch (exception: Exception) {
            exception.printStackTrace()
            Maybe.error(exception)
        }
    }

    override fun uploadAvatar(id: Int, image: String): Maybe<User> {
        return try {
            remote.uploadAvatar(id, image)
        } catch (exception: Exception) {
            exception.printStackTrace()
            Maybe.error(exception)
        }
    }

    override fun logout(id: Int, phone: String): Maybe<User> = remote.logout(id, phone)

    override fun updatePincode(id: String, pin: String): Maybe<User> = remote.updatePincode(id, pin)
}
