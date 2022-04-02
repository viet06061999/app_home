package com.apion.apionhome.di

import com.apion.apionhome.base.RxViewModel
import com.apion.apionhome.ui.add_home.AddHouseViewModel
import com.apion.apionhome.ui.detail.DetailViewModel
import com.apion.apionhome.ui.home.HomeViewModel
import com.apion.apionhome.ui.person.UserProfileViewModel
import com.apion.apionhome.ui.search.SearchViewModel
import com.apion.apionhome.ui.search.SearchViewModelHome
import com.apion.apionhome.ui.search.SearchViewModelTmp
import com.apion.apionhome.viewmodel.CommunityViewModel
import com.apion.apionhome.viewmodel.HouseViewModel
import com.apion.apionhome.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { RxViewModel() }
    viewModel { HomeViewModel(get(), get()) }
    viewModel { UserViewModel(get(), get()) }
    viewModel { HouseViewModel(get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { SearchViewModelTmp(get()) }
    viewModel { SearchViewModelHome(get()) }
    viewModel { CommunityViewModel(get()) }
    viewModel { UserProfileViewModel(get()) }
    viewModel { AddHouseViewModel(get()) }

    viewModel { DetailViewModel(get(), get()) }
}
