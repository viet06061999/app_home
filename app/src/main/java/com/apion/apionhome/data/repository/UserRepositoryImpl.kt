package com.apion.apionhome.data.repository

import com.apion.apionhome.data.model.User
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

    override fun login(phone: String, pinCode: String): Maybe<User> {
        return try {
            remote.login(phone, pinCode)
        } catch (exception: Exception) {
            Maybe.error(exception)
        }
    }
}
