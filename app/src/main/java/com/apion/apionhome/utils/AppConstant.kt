package com.apion.apionhome.utils

import java.util.*

object ApiConfig {
    const val BASE_URL = "http://apionhome.com/api/v1/"
}

object ApiEndPoint {
    const val PATH_USERS = "users"
    const val PATH_USERS_BY_ID = "users/{id}"

    const val PATH_UPLOAD_AVATAR = "upload-avatar-user/{id}"
    const val PATH_LOGOUT = "logout/{id}"
    const val PATH_LOGIN = "login"
    const val PATH_UPDATE_PIN = "update-pincode"

    const val PATH_HOUSES = "houses"
    const val PATH_HOUSES_BY_ID = "houses/{id}"
    const val PATH_SELL_HOUSE = "sell-house"

    const val PATH_DASHBOARD = "dashboards"
    const val PATH_GET_HOUSE_BY_USER = "get-houses-by-user-id/{id}"

    const val PATH_SEARCH = "search"

    const val PATH_COMMUNITY = "communities"
    const val PATH_COMMUNITY_BY_ID = "communities/{id}"

    const val PATH_PARTICIPANT = "participants"
    const val PATH_PARTICIPANT_BY_ID = "participants/{id}"
    const val PATH_LEAVE_COMMUNITY = "leave-community/{id}"

    const val PATH_BOOKMARK = "bookmarks"
    const val PATH_BOOKMARK_BY_ID = "bookmarks/{id}"
    const val PATH_UN_BOOKMARK = "leave-community/{id}"

    const val PART_PROVINCE = "province"
    const val PART_DISTRICT = "district"
    const val PART_PRICE_RANGE = "priceRange"
    const val PART_ACREAGE_RANGE = "acreageRange"
    const val PART_HOME_DIRECTION = "homeDirection"
    const val PART_TITLE = "title"
    const val PART_FRONT_WIDTH_RANGE = "frontWidthRange"
    const val PART_ATTACHMENTS = "attachment[]"

    const val PART_ATTACHMENT = "attachment"

    const val PART_NEWS_TYPE = "news_type"
    const val PART_AVATAR = "avatar"
    const val PART_COVER = "cover"

    const val PATH_PARAM_ID = "id"
}
