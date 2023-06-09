package com.travelapp.bustrainflightmobile.androidApp.adds

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.appopen.AppOpenAd.AppOpenAdLoadCallback
import com.travelapp.bustrainflightmobile.androidApp.App
import io.github.aakira.napier.Napier
import java.util.*

/** Prefetches App Open Ads.  */
class AppOpenManager(private val myApplication: App) : LifecycleObserver,
    ActivityLifecycleCallbacks {
    private var appOpenAd: AppOpenAd? = null
    private var currentActivity: Activity? = null
    private var loadTime: Long = 0
    private var loadCallback: AppOpenAdLoadCallback? = null

    /** LifecycleObserver methods  */
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
//        showAdIfAvailable()
        fetchAd()
        Log.d(LOG_TAG, "onStart")
    }

    private fun showAdIfAvailable() {
        // Only show ad if there is not already an app open ad currently showing
        // and an ad is available.
//        fetchAd()
        if (!isShowingAd && isAdAvailable) {
            Log.d(LOG_TAG, "Will show ad.")
            val fullScreenContentCallback: FullScreenContentCallback =
                object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        // Set the reference to null so isAdAvailable() returns false.
//                        appOpenAd = null
                        isShowingAd = false
//                        fetchAd()
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {}
                    override fun onAdShowedFullScreenContent() {
                        isShowingAd = true
                    }
                }
            appOpenAd!!.fullScreenContentCallback = fullScreenContentCallback
            appOpenAd!!.show(currentActivity!!)
            Napier.d("Ad shown")
        } else {
            Napier.d("AppOpenAdd: $appOpenAd")
            Log.d(LOG_TAG, "Can not show ad.")
//            fetchAd()
        }
    }

    /** Request an ad  */
    fun fetchAd() {
        Napier.d("IsAdAvailable: $isAdAvailable")
//        if (isAdAvailable) {
//            showAdIfAvailable()
//            return
//        }
        loadCallback = object : AppOpenAdLoadCallback() {
            /**
             * Called when an app open ad has loaded.
             *
             * @param ad the loaded app open ad.
             */
            override fun onAdLoaded(ad: AppOpenAd) {
//                if (appOpenAd == null){
//                    appOpenAd = ad
//                    loadTime = Date().time
//                    showAdIfAvailable()
//                } else {
                appOpenAd = ad
                loadTime = Date().time
                showAdIfAvailable()
//                }
            }

            /**
             * Called when an app open ad has failed to load.
             *
             * @param loadAdError the error.
             */
            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                // Handle the error.
            }
        }
        val request = adRequest
        AppOpenAd.load(
            myApplication, AD_UNIT_ID, request,
            AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback as AppOpenAdLoadCallback
        )
    }

    /** Creates and returns ad request.  */
    private val adRequest: AdRequest
        get() = AdRequest.Builder().build()

    /** Utility method to check if ad was loaded more than n hours ago.  */
    private fun wasLoadTimeLessThanNHoursAgo(numHours: Long): Boolean {
        val dateDifference = Date().time - loadTime
        val numMilliSecondsPerHour: Long = 3600000
        return dateDifference < numMilliSecondsPerHour * numHours
    }

    /** Utility method that checks if ad exists and can be shown.  */
    private val isAdAvailable: Boolean
        get() = appOpenAd != null && wasLoadTimeLessThanNHoursAgo(4)
//        get() = appOpenAd != null && wasLoadTimeLessThanNHoursAgo(0)

    override fun onActivityCreated(activity: Activity, bundle: Bundle?) {}
    override fun onActivityStarted(activity: Activity) {
        currentActivity = activity
    }

    override fun onActivityResumed(activity: Activity) {
        currentActivity = activity
    }

    override fun onActivityPaused(activity: Activity) {}
    override fun onActivityStopped(activity: Activity) {}
    override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {}
    override fun onActivityDestroyed(activity: Activity) {
        currentActivity = null
    }

    companion object {
        private const val LOG_TAG = "AppOpenManager"
//        private const val AD_UNIT_ID = "ca-app-pub-3940256099942544/3419835294"
        private const val AD_UNIT_ID = "ca-app-pub-7574006463043131/9295024520"
        private var isShowingAd = false
    }

    /** Constructor  */
    init {
        myApplication.registerActivityLifecycleCallbacks(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }
}