package com.apion.apionhome.ui.search

import com.apion.apionhome.R
import com.apion.apionhome.data.model.local.LocationName

class SearchStreetFragment : SearchLocationBottomSheet<LocationName>() {

    override fun getHint(): String {
        return getString(R.string.title_search_street)
    }

    override fun onItemClick(item: LocationName) {
        viewModel.setStreet(item)
    }

    override fun onSearch(query: String) {
        viewModel.searchStreet(query)
    }
}
