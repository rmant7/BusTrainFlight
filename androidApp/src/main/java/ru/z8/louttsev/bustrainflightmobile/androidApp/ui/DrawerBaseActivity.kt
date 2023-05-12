package ru.z8.louttsev.bustrainflightmobile.androidApp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import ru.z8.louttsev.bustrainflightmobile.androidApp.R
import ru.z8.louttsev.bustrainflightmobile.androidApp.databinding.ActivityDrawerBaseBinding

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
                overridePendingTransition(0, 0)
            }
            budgetTravelTipsOption.setOnClickListener {
                startActivity(Intent(this@DrawerBaseActivity, TravelTipsActivity::class.java))
                overridePendingTransition(0, 0)
            }
            contactsOption.setOnClickListener {
                startActivity(Intent(this@DrawerBaseActivity, ContactsActivity::class.java))
                overridePendingTransition(0, 0)
            }
        }
    }
}