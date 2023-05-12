package ru.z8.louttsev.bustrainflightmobile.androidApp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.MotionEvent
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import ru.z8.louttsev.bustrainflightmobile.androidApp.R
import ru.z8.louttsev.bustrainflightmobile.androidApp.databinding.ActivityTravelTipsBinding

class TravelTipsActivity : DrawerBaseActivity() {

    private lateinit var binding: ActivityTravelTipsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTravelTipsBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        drawerBaseBinding.root.findViewById<TextView>(R.id.appBarTitle).text = "BudgetTravelTips"

        with(binding){
            webView.loadUrl("https://heartfelt-pothos-d1481c.netlify.app/en-US/#/travel")
            webView.settings.javaScriptEnabled = true
//            startActivity(Intent(this@TravelTipsActivity, MainActivity::class.java))
            // For loading url inside app
//            webView.webViewClient = WebViewClient()
        }
    }
}