package ru.z8.louttsev.bustrainflightmobile.androidApp

import android.app.Application
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

import ru.z8.louttsev.bustrainflightmobile.androidApp.di.repositoryModule
import ru.z8.louttsev.bustrainflightmobile.androidApp.di.utilsModule
import ru.z8.louttsev.bustrainflightmobile.androidApp.di.viewModelModule

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
                    viewModelModule,
                    utilsModule
                )
            )
        }

        Napier.base(DebugAntilog())

        YandexMetrica.activate(
            applicationContext,
            YandexMetricaConfig
                .newConfigBuilder("40914165-705b-466c-aa95-f1f7ade0606d")
                .build()
        )
        YandexMetrica.enableActivityAutoTracking(this)

//        convertToString = { toString(this@App) }

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