package com.apion.apionhome.ui.search

import com.apion.apionhome.R
import com.apion.apionhome.data.model.local.LocationName

class SearchWardFragment : SearchLocationFragment<LocationName>() {

    override fun onItemClick(item: LocationName) {
        viewModel.setWard(item)
    }

    override fun onGetAll(query: String) {
        viewModel.searchWard(query)
    }

    override fun getHint(): String {
        return  getString(R.string.title_search_ward)
    }
}