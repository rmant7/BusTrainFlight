package ru.z8.louttsev.bustrainflightmobile.androidApp.model.data

import android.content.Context
import android.widget.Toast
import org.json.JSONObject
import ru.z8.louttsev.bustrainflightmobile.androidApp.R
import java.io.IOException
import kotlin.collections.iterator

fun loadCityNamesJsonFromRaw(context: Context): String {
    try {
        val inputStream = context.resources.openRawResource(R.raw.city_names_for_pathes)
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        val jsonString = String(buffer, Charsets.UTF_8)
        val cityNamesJson = JSONObject(jsonString)
        for (key in cityNamesJson.keys()) {
            val cityName = cityNamesJson.getString(key)
        }

        return jsonString

    } catch (ex: IOException) {
        ex.printStackTrace()

        Toast.makeText(context, "Error loading city names", Toast.LENGTH_SHORT).show()
        return ""
    }
}