package com.apion.apionhome.data.source.remote.utils

import com.apion.apionhome.data.model.User
import com.apion.apionhome.data.source.remote.response_entity.AllUserResponse
import com.apion.apionhome.data.source.remote.response_entity.HouseResponse
import com.apion.apionhome.data.source.remote.response_entity.UserResponse
import com.apion.apionhome.utils.ApiEndPoint
import com.apion.apionhome.utils.ApiEndPoint.PATH_LOGIN
import com.apion.apionhome.utils.ApiEndPoint.PATH_LOGOUT
import com.apion.apionhome.utils.ApiEndPoint.PATH_PARAM_ID
import com.apion.apionhome.utils.ApiEndPoint.PATH_USERS
import com.apion.apionhome.utils.ApiEndPoint.PATH_USERS_BY_ID
import io.reactivex.rxjava3.core.Maybe
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface UserAPIService {

    @GET(PATH_USERS)
    fun geUsers(): Maybe<AllUserResponse>

    @GET(PATH_USERS_BY_ID)
    fun geUserById(@Path(PATH_PARAM_ID) id: Int): Maybe<UserResponse>

    @POST(PATH_USERS)
    fun createUser(@Body user: User): Maybe<UserResponse>

    @POST(PATH_USERS_BY_ID)
    fun updateUser(@Path(PATH_PARAM_ID) id: Int, @Body user: User): Maybe<UserResponse>

    @POST(PATH_LOGIN)
    fun login(@Body phone: RequestBody): Maybe<UserResponse>

    @POST(PATH_LOGOUT)
    fun logout(
        @Path(PATH_PARAM_ID) id: Int,
        @Body phone: RequestBody
    ): Maybe<UserResponse>

    @Multipart
    @POST(ApiEndPoint.PATH_UPLOAD_AVATAR)
    @JvmSuppressWildcards
    fun uploadAvatar(
        @Path(PATH_PARAM_ID) id: Int,
        @Part attachment: MultipartBody.Part
    ): Maybe<UserResponse>
}
