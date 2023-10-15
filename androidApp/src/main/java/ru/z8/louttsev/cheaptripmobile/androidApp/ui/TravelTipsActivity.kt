package ru.z8.louttsev.cheaptripmobile.androidApp.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import ru.z8.louttsev.cheaptripmobile.androidApp.databinding.ActivityTravelTipsBinding

class TravelTipsActivity : DrawerBaseActivity() {

    private lateinit var binding: ActivityTravelTipsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTravelTipsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Open Telegram
        val telegramAppPackage = "org.telegram.messenger"
        val telegramChannelUri = "https://t.me/bustrainflightferry"
        val telegramIntent = Intent(Intent.ACTION_VIEW, Uri.parse(telegramChannelUri))
        telegramIntent.setPackage(telegramAppPackage)

        val packageManager = applicationContext.packageManager
        val activities =
            packageManager.queryIntentActivities(telegramIntent, PackageManager.MATCH_DEFAULT_ONLY)

        if (activities.isNotEmpty()) {
            startActivity(telegramIntent)
        } else {

            val facebookAppPackage = "com.facebook.katana"
            val facebookPageUri = "fb://facewebmodal/f?href=https://m.facebook.com/cheaptripguru"
            val facebookIntent = Intent(Intent.ACTION_VIEW, Uri.parse(facebookPageUri))
            facebookIntent.setPackage(facebookAppPackage)

            val packageManager = applicationContext.packageManager
            val activities = packageManager.queryIntentActivities(
                facebookIntent,
                PackageManager.MATCH_DEFAULT_ONLY
            )

            if (activities.isNotEmpty()) {
                startActivity(facebookIntent)
            } else {
                val facebookIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.facebook.com/cheaptripguru?mibextid=ZbWKwL")
                )

                val packageManager = applicationContext.packageManager
                val activities = packageManager.queryIntentActivities(
                    facebookIntent,
                    PackageManager.MATCH_DEFAULT_ONLY
                )

                if (activities.isNotEmpty()) {
                    startActivity(facebookIntent)
                }
            }
        }

//        drawerBaseBinding.root.findViewById<TextView>(R.id.appBarTitle).text = "BudgetTravelTips"

    }
}