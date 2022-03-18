package com.apion.apionhome.ui.search

import com.apion.apionhome.R
import com.apion.apionhome.data.model.local.District
import com.apion.apionhome.data.model.local.Province

class SearchDistrictFragment : SearchLocationBottomSheet<District>() {

    override fun getHint(): String {
        return getString(R.string.title_search_district)
    }

    override fun onItemClick(item: District) {
        println("wards ${item.wards}")
        viewModel.setDistrict(item)
    }

    override fun onSearch(query: String) {
        viewModel.searchDistrict(query)
    }
}
