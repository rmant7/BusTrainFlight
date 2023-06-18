package ru.z8.louttsev.bustrainflightmobile.androidApp.ui

import android.content.Intent
import android.os.Bundle
import ru.z8.louttsev.bustrainflightmobile.androidApp.databinding.ActivityTravelTipsBinding

class TravelTipsActivity : DrawerBaseActivity() {

    private lateinit var binding: ActivityTravelTipsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTravelTipsBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        drawerBaseBinding.root.findViewById<TextView>(R.id.appBarTitle).text = "BudgetTravelTips"

        with(binding){
            webView.loadUrl("https://cheaptrip.guru")
            webView.settings.javaScriptEnabled = true
            startActivity(Intent(this@TravelTipsActivity, MainActivity::class.java))
            // For loading url inside app
//            webView.webViewClient = WebViewClient()
        }
    }
}