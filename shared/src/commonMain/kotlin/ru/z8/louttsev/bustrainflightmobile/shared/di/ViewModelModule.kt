package ru.z8.louttsev.bustrainflightmobile.shared.di

import org.koin.dsl.module
import ru.z8.louttsev.bustrainflightmobile.shared.viewmodel.MainViewModel

val viewModelModule = module {
    single { MainViewModel(get(), get()) }
}