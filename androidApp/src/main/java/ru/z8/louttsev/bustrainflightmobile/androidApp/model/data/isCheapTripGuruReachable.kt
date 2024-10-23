package ru.z8.louttsev.bustrainflightmobile.androidApp.model.data

import android.app.AlertDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL
import javax.net.ssl.HttpsURLConnection

fun isCheapTripGuruReachable(url: String, completion: (Boolean) -> Unit){
    CoroutineScope(Dispatchers.IO + SupervisorJob()).launch{
        val reachable = try {
            val connection = URL(url).openConnection() as HttpsURLConnection
            connection.requestMethod = "HEAD"
            connection.connectTimeout = 5000
            connection.responseCode == HttpsURLConnection.HTTP_OK
        } catch (e: Exception) {
            false
        }
        withContext(Dispatchers.Main){
            completion(reachable)
        }
    }
}

