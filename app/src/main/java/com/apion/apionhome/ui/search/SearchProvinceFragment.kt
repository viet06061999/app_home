package com.apion.apionhome.ui.search

import com.apion.apionhome.R
import com.apion.apionhome.data.model.local.Province

class SearchProvinceFragment : SearchLocationBottomSheet<Province>() {

    override fun getHint(): String {
        return getString(R.string.title_search_province)
    }

    override fun onItemClick(item: Province) {
        println("wards ${item.districts}")
        viewModel.setProvince(item)
    }

    override fun onSearch(query: String) {
        viewModel.searchProvince(query)
    }

    override fun onConnectionAvailable() {
    }
}
