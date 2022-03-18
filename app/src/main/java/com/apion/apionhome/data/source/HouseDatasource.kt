package com.apion.apionhome.data.source

import com.apion.apionhome.data.model.dashboard.Dashboard
import com.apion.apionhome.data.model.House
import com.apion.apionhome.data.model.SearchParam
import com.apion.apionhome.data.model.local.District
import com.apion.apionhome.data.model.local.LocationName
import com.apion.apionhome.data.model.local.Province
import io.reactivex.rxjava3.core.Maybe

interface HouseDatasource {

    interface Local {
        fun getAllProvince(): Maybe<List<Province>>

        fun searchProvince(query: String): Maybe<List<Province>>

        fun searchDistrict(province: Province? = null, query: String): Maybe<List<District>>

        fun searchWard(
            district: District? = null,
            query: String
        ): Maybe<List<LocationName>>

        fun searchStreet(
            district: District? = null,
            query: String
        ): Maybe<List<LocationName>>

        fun searchProject(
            district: District? = null,
            query: String
        ): Maybe<List<LocationName>>

        fun searchLocation(query: String): Maybe<List<Province>>
    }

    interface Remote {

        fun getAllHouses(): Maybe<List<House>>

        fun getHouseById(houseId: Int): Maybe<House>

        fun getDashboard(): Maybe<Dashboard>

        fun getHouseByUser(userId: Int): Maybe<List<House>>

        fun getSearchHouse(searchParam: SearchParam): Maybe<List<House>>

        fun createHouse(images: List<String>, house: House): Maybe<House>

        fun updateHouse(images: List<String>, house: House): Maybe<House>
    }
}
