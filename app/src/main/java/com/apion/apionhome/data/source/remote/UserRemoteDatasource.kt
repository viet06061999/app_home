package com.apion.apionhome.data.source.remote

import com.apion.apionhome.data.model.User
import com.apion.apionhome.data.model.UserFollowRequest
import com.apion.apionhome.data.model.UserFollowed
import com.apion.apionhome.data.source.UserDatasource
import com.apion.apionhome.data.source.remote.utils.UserAPIService
import com.apion.apionhome.utils.ApiEndPoint
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging
import com.google.gson.Gson
import com.google.gson.JsonObject
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.File
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.util.*


class UserRemoteDatasource(private val backend: UserAPIService) : UserDatasource.Remote {

    override fun getAllUsers(): Maybe<List<User>> = backend.geUsers().map { it.users }

    override fun getUserById(id: Int): Maybe<User> = backend.geUserById(id).map { it.user }

    @Throws(IllegalArgumentException::class)
    override fun createUser(user: User): Maybe<User> {
        return try {
            backend.createUser(user).map {
                if (it.isSuccess) it.user else throw IllegalArgumentException(it.message)
            }
        } catch (exception: Exception) {
            Maybe.error(exception)
        }
    }

    @Throws(IllegalArgumentException::class)
    override fun updateUser(user: User): Maybe<User> {
        return try {
            user.id?.let {
                backend.updateUser(user.id, user).map {
                    if (it.isSuccess) it.user else throw IllegalArgumentException(it.message)
                }
            } ?: Maybe.error(Exception("khong tim thay user"))

        } catch (exception: Exception) {
            Maybe.error(exception)
        }
    }

    override fun uploadAvatar(id: Int, image: String): Maybe<User> {
        val file = File(image)
        return if (file.isFile) {
            val end = file.name.split(".").last()
            val currentTime = Date().time.toString()
            val fileName = "$currentTime.$end"
            val imageRequestBody = RequestBody.create(MediaType.parse("image/*"), file)
            val imagePart = MultipartBody.Part.createFormData(
                ApiEndPoint.PART_ATTACHMENT,
                fileName,
                imageRequestBody
            )
            backend.uploadAvatar(id, imagePart).map { it.user }
        } else {
            Maybe.error(IllegalArgumentException("No such file $image"))
        }
    }

    @Throws(IllegalArgumentException::class)
    override fun login(phone: String): Maybe<User> {
        val json = JsonObject().apply {
            addProperty("phone", phone)
        }

        val body = RequestBody.create(
            MediaType.parse("application/json; charset=utf-8"),
            Gson().toJson(json)
        )

        return try {
            backend.login(body).map {
                if (it.isSuccess) {
                    it.user
                } else {
                    throw IllegalArgumentException(it.message)
                }
            }
        } catch (exception: Exception) {

            Maybe.error(exception)
        }
    }


    override fun logout(id: Int, phone: String): Maybe<User> {
        val json = JsonObject().apply {
            addProperty("phone", phone)
        }

        val body = RequestBody.create(
            MediaType.parse("application/json; charset=utf-8"),
            Gson().toJson(json)
        )
        return try {
            backend.logout(id, body).map {
                if (it.isSuccess) {
                    it.user
                } else {
                    throw IllegalArgumentException(it.message)
                }
            }
        } catch (exception: HttpException) {
            Maybe.error(exception)
        }
    }

    override fun updatePincode(id: String, pin: String): Maybe<User> {
        val json = JsonObject().apply {
            addProperty("pincode", pin)
        }

        val body = RequestBody.create(
            MediaType.parse("application/json; charset=utf-8"),
            Gson().toJson(json)
        )

        return try {
            backend.updatePincode(id, body).map {
                if (it.isSuccess) {
                    it.user
                } else {
                    throw IllegalArgumentException(it.message)
                }
            }
        } catch (exception: Exception) {

            Maybe.error(exception)
        }
    }

    override fun follow(followerId: Int, beingFollowedId: Int): Maybe<UserFollowed> {


        val body = UserFollowRequest(followerId, beingFollowedId)
        return try {
            backend.follow(body).map {
                if (it.isSuccess) {
                    FirebaseMessaging.getInstance().subscribeToTopic("ApionHome$beingFollowedId")
                        .addOnCompleteListener { task ->
                            if (!task.isSuccessful) {
                                throw IllegalArgumentException(task.exception)
                            }
                        }
                    it.userFollow
                } else {
                    throw IllegalArgumentException(it.message)
                }
            }
        } catch (exception: HttpException) {
            Maybe.error(exception)
        }
    }

    override fun unFollow(followerId: Int, beingFollowedId: Int): Maybe<UserFollowed> {

        val body = UserFollowRequest(followerId, beingFollowedId)
        return try {
            backend.unFollow(body).map {
                if (it.isSuccess) {
                    FirebaseMessaging.getInstance()
                        .unsubscribeFromTopic("ApionHome$beingFollowedId")
                        .addOnCompleteListener { task ->
                            if (!task.isSuccessful) {
                                throw IllegalArgumentException(task.exception)
                            }
                        }
                    it.userFollow
                } else {
                    throw IllegalArgumentException(it.message)
                }
            }
        } catch (exception: HttpException) {
            Maybe.error(exception)
        }
    }

    companion object {
        const val AUTHEN_EXCEPTION = "Password isn't valid"
    }
}
