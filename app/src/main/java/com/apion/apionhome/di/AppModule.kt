package com.apion.apionhome.di

import com.apion.apionhome.base.RxViewModel
import com.apion.apionhome.ui.detail.DetailViewModel
import com.apion.apionhome.ui.home.HomeViewModel
import com.apion.apionhome.ui.search.SearchViewModel
import com.apion.apionhome.viewmodel.CommunityViewModel
import com.apion.apionhome.viewmodel.HouseViewModel
import com.apion.apionhome.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { RxViewModel() }
    viewModel { HomeViewModel(get(), get()) }
    viewModel { UserViewModel(get()) }
    viewModel { HouseViewModel(get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { CommunityViewModel(get()) }
    viewModel { DetailViewModel(get(), get()) }
}
