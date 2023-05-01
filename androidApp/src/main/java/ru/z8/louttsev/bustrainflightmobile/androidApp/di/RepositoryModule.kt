package ru.z8.louttsev.bustrainflightmobile.androidApp.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.z8.louttsev.bustrainflightmobile.androidApp.infrastructure.persistence.LocationsDbJson
import ru.z8.louttsev.bustrainflightmobile.androidApp.infrastructure.persistence.RoutesDbJson
import ru.z8.louttsev.bustrainflightmobile.androidApp.model.LocationsRepositoryJson
import ru.z8.louttsev.bustrainflightmobile.androidApp.model.RoutesRepositoryJson

val repositoryModule = module {
    single { LocationsDbJson(androidContext()) }
    single { RoutesDbJson(androidContext()) }
    single { LocationsRepositoryJson(get()) }
    single { RoutesRepositoryJson(get(), get()) }
}