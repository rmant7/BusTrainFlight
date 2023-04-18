package ru.z8.louttsev.cheaptripmobile.shared.di

import org.koin.dsl.module
import ru.z8.louttsev.cheaptripmobile.shared.infrastructure.persistence.LocationsDbJson
import ru.z8.louttsev.cheaptripmobile.shared.infrastructure.persistence.RoutesDbJson
import ru.z8.louttsev.cheaptripmobile.shared.model.LocationsRepositoryJson
import ru.z8.louttsev.cheaptripmobile.shared.model.RoutesRepositoryJson

val repositoryModule = module {
    single { LocationsDbJson() }
    single { RoutesDbJson() }
    single { LocationsRepositoryJson(get()) }
    single { RoutesRepositoryJson(get(), get()) }
}