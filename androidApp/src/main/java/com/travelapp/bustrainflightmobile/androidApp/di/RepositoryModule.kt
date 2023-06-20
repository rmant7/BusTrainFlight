package com.travelapp.bustrainflightmobile.androidApp.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import com.travelapp.bustrainflightmobile.androidApp.infrastructure.persistence.LocationsDbJson
import com.travelapp.bustrainflightmobile.androidApp.infrastructure.persistence.RoutesDbJson
import com.travelapp.bustrainflightmobile.androidApp.model.LocationRepository
import com.travelapp.bustrainflightmobile.androidApp.model.RouteRepository

val repositoryModule = module {
    single { LocationsDbJson(androidContext()) }
    single { RoutesDbJson(androidContext()) }
    single { LocationRepository(get()) }
    single { RouteRepository(get(), get()) }
}