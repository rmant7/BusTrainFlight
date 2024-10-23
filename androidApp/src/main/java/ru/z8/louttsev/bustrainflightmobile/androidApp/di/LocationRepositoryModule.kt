package ru.z8.louttsev.bustrainflightmobile.androidApp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.z8.louttsev.bustrainflightmobile.androidApp.infrastructure.persistence.LocationsDbJson
import ru.z8.louttsev.bustrainflightmobile.androidApp.model.LocationRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationRepositoryModule {

    @Provides
    @Singleton
    fun provideLocationRepository(db: LocationsDbJson): LocationRepository {
        return LocationRepository(db)
    }

    @Provides
    @Singleton
    fun provideLocationsDbJson(@ApplicationContext context: Context): LocationsDbJson {
        return LocationsDbJson(context)
    }
}