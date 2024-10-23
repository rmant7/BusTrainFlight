package ru.z8.louttsev.bustrainflightmobile.androidApp

import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig
import dagger.hilt.android.HiltAndroidApp
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import ru.z8.louttsev.bustrainflightmobile.androidApp.ads.AppOpenManager


/**
 * Declares DI container.
 */
@HiltAndroidApp
class App : Application(){

    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(
            this
        ) { }
        appOpenManager = AppOpenManager(this)


        Napier.base(DebugAntilog())

        YandexMetrica.activate(
            applicationContext,
            YandexMetricaConfig
                .newConfigBuilder("40914165-705b-466c-aa95-f1f7ade0606d")
                .build()
        )
        YandexMetrica.enableActivityAutoTracking(this)

    }

    companion object {
        private var appOpenManager: AppOpenManager? = null
    }
}