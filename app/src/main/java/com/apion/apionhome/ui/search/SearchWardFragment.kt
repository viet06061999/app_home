package com.apion.apionhome.ui.search

import com.apion.apionhome.R
import com.apion.apionhome.data.model.local.LocationName

class SearchWardFragment : SearchLocationBottomSheet<LocationName>() {

    override fun getHint(): String {
        return  getString(R.string.title_search_ward)
    }

    override fun onItemClick(item: LocationName) {
        println("tittle ${item.prefix} ${item.name}")
        viewModel.setWard(item)
    }

    override fun onSearch(query: String) {
        viewModel.searchWard(query)
    }
}
