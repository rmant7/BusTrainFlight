package ru.z8.louttsev.cheaptripmobile.androidApp.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.z8.louttsev.cheaptripmobile.androidApp.infrastructure.persistence.LocationsDbJson
import ru.z8.louttsev.cheaptripmobile.androidApp.infrastructure.persistence.RoutesDbJson
import ru.z8.louttsev.cheaptripmobile.androidApp.model.LocationRepository
import ru.z8.louttsev.cheaptripmobile.androidApp.model.RouteRepository

val repositoryModule = module {
    single { LocationsDbJson(androidContext()) }
    single { RoutesDbJson(androidContext()) }
    single { LocationRepository(get()) }
    single { RouteRepository(get(), get()) }
}