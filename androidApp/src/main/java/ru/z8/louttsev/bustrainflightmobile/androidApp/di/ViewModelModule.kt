package ru.z8.louttsev.bustrainflightmobile.androidApp.di

import org.koin.dsl.module
import ru.z8.louttsev.bustrainflightmobile.androidApp.viewmodel.MainViewModel

val viewModelModule = module {
    single { MainViewModel(get(), get()) }
}