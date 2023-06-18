package ru.z8.louttsev.bustrainflightmobile.androidApp.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.z8.louttsev.bustrainflightmobile.androidApp.model.data.DurationConverter

val utilsModule = module {
    single { DurationConverter(androidContext()) }
}