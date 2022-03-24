package com.apion.apionhome.ui.search

import com.apion.apionhome.data.model.local.District

class SearchDistrictFragment : SearchLocationFragment<District>() {
    override fun onItemClick(item: District) {
        viewModel.setDistrict(item)
    }

    override fun onGetAll(query: String) {
        viewModel.searchDistrict("")
    }

    override fun getHint(): String {
        return "Tìm kiếm"
    }
}