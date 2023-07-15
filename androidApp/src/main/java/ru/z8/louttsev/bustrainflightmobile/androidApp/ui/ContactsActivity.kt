package ru.z8.louttsev.bustrainflightmobile.androidApp.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import ru.z8.louttsev.bustrainflightmobile.androidApp.R
import ru.z8.louttsev.bustrainflightmobile.androidApp.databinding.ActivityContactsBinding

class ContactsActivity : DrawerBaseActivity() {

    private lateinit var binding: ActivityContactsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        drawerBaseBinding.root.findViewById<TextView>(R.id.appBarTitle).text = "Contacts"

        with(binding) {
            telegramImageView.setOnClickListener {
                try {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("tg://resolve?domain=rmant7"))
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    try {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("market://details?id=org.telegram.messenger")
                            )
                        )
                    } catch (ex: ActivityNotFoundException) {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://play.google.com/store/apps/details?id=org.telegram.messenger")
                            )
                        )
                    }
                }
            }
            whatsAppImageView.setOnClickListener {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://api.whatsapp.com/send?phone=+972545779239")
                )
                startActivity(intent)
            }
//            mobileImageView.setOnClickListener {
//                val dialIntent = Intent(Intent.ACTION_DIAL)
//                dialIntent.data = Uri.parse("tel:972545779239")
//
//                if (dialIntent.resolveActivity(packageManager) != null) {
//                    startActivity(dialIntent)
//                } else {
//                    Toast.makeText(this@ContactsActivity, "Error", Toast.LENGTH_LONG)
//                        .show()
//                }
//            }
            emailTextView.setOnClickListener {
                val mailto = "mailto:roman.mantelmakher@gmail.com" +
                        "?subject=" + Uri.encode("BusTrainFlight - Support")
                val emailIntent = Intent(Intent.ACTION_SENDTO)
                emailIntent.data = Uri.parse(mailto)
                try {
                    startActivity(emailIntent)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(this@ContactsActivity, "There are no email clients installed", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }

    }
}