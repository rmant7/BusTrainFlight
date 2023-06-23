package ru.z8.louttsev.bustrainflightmobile.androidApp.di

import org.koin.dsl.module
import ru.z8.louttsev.bustrainflightmobile.androidApp.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel

val viewModelModule = module {
    viewModel { MainViewModel(get(), get()) }
}