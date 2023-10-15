package ru.z8.louttsev.cheaptripmobile.androidApp.di

import org.koin.dsl.module
import ru.z8.louttsev.cheaptripmobile.androidApp.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel

val viewModelModule = module {
    viewModel { MainViewModel(get(), get()) }
}