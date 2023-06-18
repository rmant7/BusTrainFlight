package ru.z8.louttsev.bustrainflightmobile.androidApp

import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.initialization.InitializationStatus
import ru.z8.louttsev.bustrainflightmobile.androidApp.adds.AppOpenManager
import ru.z8.louttsev.bustrainflightmobile.androidApp.di.repositoryModule
import ru.z8.louttsev.bustrainflightmobile.androidApp.di.utilsModule
import ru.z8.louttsev.bustrainflightmobile.androidApp.di.viewModelModule
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


/**
 * Declares DI container.
 */
class App : Application(){

    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(
            this
        ) { }
        appOpenManager = AppOpenManager(this)

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

//        YandexMetrica.activate(
//            applicationContext,
//            YandexMetricaConfig
//                .newConfigBuilder("40914165-705b-466c-aa95-f1f7ade0606d")
//                .build()
//        )
//        YandexMetrica.enableActivityAutoTracking(this)

    }

    companion object {
        private var appOpenManager: AppOpenManager? = null
    }
}