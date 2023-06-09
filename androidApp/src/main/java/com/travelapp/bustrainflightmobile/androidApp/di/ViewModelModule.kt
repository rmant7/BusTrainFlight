package com.travelapp.bustrainflightmobile.androidApp.di

import org.koin.dsl.module
import com.travelapp.bustrainflightmobile.androidApp.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel

val viewModelModule = module {
    viewModel { MainViewModel(get(), get()) }
}