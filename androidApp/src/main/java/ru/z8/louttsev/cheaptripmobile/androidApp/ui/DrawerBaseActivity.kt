package ru.z8.louttsev.cheaptripmobile.androidApp.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import io.github.aakira.napier.Napier
import ru.z8.louttsev.cheaptripmobile.androidApp.R
import ru.z8.louttsev.cheaptripmobile.androidApp.databinding.ActivityDrawerBaseBinding

open class DrawerBaseActivity : AppCompatActivity() {

    protected lateinit var drawerBaseBinding: ActivityDrawerBaseBinding

    override fun setContentView(view: View?) {

        drawerBaseBinding = ActivityDrawerBaseBinding.inflate(layoutInflater)

        val container = drawerBaseBinding.root.findViewById<FrameLayout>(R.id.activityContainer)
        container.addView(view)
        super.setContentView(drawerBaseBinding.root)

        val toolbar = drawerBaseBinding.root.findViewById<Toolbar>(R.id.toolBar)
        setSupportActionBar(toolbar)

        val menu = toolbar.rootView.findViewById<ImageView>(R.id.menuButton)
        menu.setOnClickListener {
            drawerBaseBinding.drawerLayout.openDrawer(GravityCompat.END)
        }

        with(drawerBaseBinding){
            closeDrawerButton.setOnClickListener {
                drawerLayout.closeDrawer(GravityCompat.END)
            }

            busTrainFlightOption.setOnClickListener {
                startActivity(Intent(this@DrawerBaseActivity, MainActivity::class.java))
                finish()
                overridePendingTransition(0, 0)
            }
            budgetTravelTipsOption.setOnClickListener {
                openBudgetTravelTips()
//                startActivity(Intent(this@DrawerBaseActivity, TravelTipsActivity::class.java))
//                finish()
//                overridePendingTransition(0, 0)
            }
            contactsOption.setOnClickListener {
                startActivity(Intent(this@DrawerBaseActivity, ContactsActivity::class.java))
                finish()
                overridePendingTransition(0, 0)
            }
        }
    }

    fun telegramIntent(): Intent {
        val pageId = "bustrainflightferry"
        var intent: Intent? = null
        try {
            try {
                applicationContext.packageManager.getPackageInfo("org.telegram.messenger", 0)//Check for Telegram Messenger App
            } catch (e : Exception){
                applicationContext.packageManager.getPackageInfo("org.thunderdog.challegram", 0)//Check for Telegram X App
            }
            intent = Intent(Intent.ACTION_VIEW, Uri.parse("tg://resolve?domain=${pageId}"))
        }catch (e : Exception){ //App not found open in browser
            intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.telegram.me/$pageId"))
        }
        return intent!!
    }

    private fun openBudgetTravelTips() {
        // Open Telegram
        val telegramAppPackage = "org.telegram.messenger"
        val telegramChannelUri = "https://t.me/bustrainflightferry"
        val telegramIntent = Intent(Intent.ACTION_VIEW, Uri.parse(telegramChannelUri))
//        val telegramIntent = telegramIntent()
        telegramIntent.setPackage(telegramAppPackage)

        val packageManager = applicationContext.packageManager
        val activities =
            packageManager.queryIntentActivities(telegramIntent, PackageManager.MATCH_DEFAULT_ONLY)

        Napier.d(activities.toString())
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

            Napier.d("$activities")
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
    }
}