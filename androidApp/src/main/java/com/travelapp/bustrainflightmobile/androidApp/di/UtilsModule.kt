package com.travelapp.bustrainflightmobile.androidApp.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import com.travelapp.bustrainflightmobile.androidApp.model.data.DurationConverter

val utilsModule = module {
    single { DurationConverter(androidContext()) }
}