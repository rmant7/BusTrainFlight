package ru.z8.louttsev.bustrainflightmobile.androidApp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.z8.louttsev.bustrainflightmobile.androidApp.infrastructure.persistence.LocationsDbJson
import ru.z8.louttsev.bustrainflightmobile.androidApp.infrastructure.persistence.RoutesDbJson
import ru.z8.louttsev.bustrainflightmobile.androidApp.model.LocationRepository
import ru.z8.louttsev.bustrainflightmobile.androidApp.model.RouteRepository
import ru.z8.louttsev.bustrainflightmobile.androidApp.model.data.DurationConverter
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RouteRepositoryModule {

    @Provides
    @Singleton
    fun provideRouteRepository(
        durationConverter: DurationConverter,
        db: RoutesDbJson,
        locationRepository: LocationRepository
    ): RouteRepository {
        return RouteRepository(durationConverter, db, locationRepository)
    }

    @Provides
    @Singleton
    fun provideRoutesDbJson(@ApplicationContext context: Context): RoutesDbJson {
        return RoutesDbJson(context)
    }

    @Provides
    @Singleton
    fun provideDurationConverter(@ApplicationContext context: Context): DurationConverter {
        return DurationConverter(context)
    }
}