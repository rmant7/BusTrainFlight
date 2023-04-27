package ru.z8.louttsev.bustrainflightmobile.shared.di

import org.koin.dsl.module
import ru.z8.louttsev.bustrainflightmobile.shared.infrastructure.persistence.LocationsDbJson
import ru.z8.louttsev.bustrainflightmobile.shared.infrastructure.persistence.RoutesDbJson
import ru.z8.louttsev.bustrainflightmobile.shared.model.LocationsRepositoryJson
import ru.z8.louttsev.bustrainflightmobile.shared.model.RoutesRepositoryJson

val repositoryModule = module {
    single { LocationsDbJson() }
    single { RoutesDbJson() }
    single { LocationsRepositoryJson(get()) }
    single { RoutesRepositoryJson(get(), get()) }
}