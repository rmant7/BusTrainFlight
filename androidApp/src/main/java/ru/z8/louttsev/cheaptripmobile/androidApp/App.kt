package ru.z8.louttsev.cheaptripmobile.androidApp

import android.app.Application
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.z8.louttsev.cheaptripmobile.shared.DatabaseDriverFactory
import ru.z8.louttsev.cheaptripmobile.shared.convertToString
import ru.z8.louttsev.cheaptripmobile.shared.infrastructure.datasource.FullDb
import ru.z8.louttsev.cheaptripmobile.shared.infrastructure.datasource.LocationDataSourceFullDb
import ru.z8.louttsev.cheaptripmobile.shared.infrastructure.datasource.RouteDataSourceFullDb
import ru.z8.louttsev.cheaptripmobile.shared.infrastructure.persistence.LocalDb
import ru.z8.louttsev.cheaptripmobile.shared.infrastructure.persistence.LocationDb
import ru.z8.louttsev.cheaptripmobile.shared.infrastructure.persistence.RouteDb
import ru.z8.louttsev.cheaptripmobile.shared.model.LocationRepository
import ru.z8.louttsev.cheaptripmobile.shared.model.RepositoryStrategy.*
import ru.z8.louttsev.cheaptripmobile.shared.model.RouteRepository

import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.z8.louttsev.cheaptripmobile.shared.di.repositoryModule
import ru.z8.louttsev.cheaptripmobile.shared.di.viewModelModule

/**
 * Declares DI container.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    repositoryModule,
                    viewModelModule
                )
            )
        }

        Napier.base(DebugAntilog())

        YandexMetrica.activate(
            applicationContext,
            YandexMetricaConfig
                .newConfigBuilder("cfc4505a-fc5f-499d-abe1-e571d77f9d52")
                .build()
        )
        YandexMetrica.enableActivityAutoTracking(this)

        convertToString = { toString(this@App) }

//        val fullDbDriver = DatabaseDriverFactory(this).getDriver(FullDb.Schema, "fullDb.sqlite3")
//        val localDbDriver =
//            DatabaseDriverFactory(this).createDriver(LocalDb.Schema, "localDb.sqlite3")
//
//        sLocationRepository = LocationRepository(
//            // TODO change to network implementation, issue #1
//            mainSource = LocationDataSourceFullDb(fullDbDriver),
//            reserveSource = LocationDb(localDbDriver),
//            strategy = DIRECT_READ
//        )
//
//        sRouteRepository = RouteRepository(
//            // TODO change to network implementation, issue #1
//            mainSource = RouteDataSourceFullDb(fullDbDriver),
//            reserveSource = RouteDb(localDbDriver),
//            strategy = DIRECT_READ // TODO change to BACKUP, issue #1 (first solve issue #10)
//        )
    }
//
//    /**
//     * Access point to initiated repositories.
//     *
//     * @property sLocationRepository Read-only storage of available locations.
//     * @property sRouteRepository Read-only storage of available routes.
//     */
//    companion object {
//        lateinit var sLocationRepository: LocationRepository
//        lateinit var sRouteRepository: RouteRepository
//    }
}