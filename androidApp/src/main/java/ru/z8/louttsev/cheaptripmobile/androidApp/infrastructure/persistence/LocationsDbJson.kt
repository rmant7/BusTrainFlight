package ru.z8.louttsev.cheaptripmobile.androidApp.infrastructure.persistence

import android.content.Context
import kotlinx.serialization.json.Json
import ru.z8.louttsev.cheaptripmobile.androidApp.R
import ru.z8.louttsev.cheaptripmobile.androidApp.model.data.LocationJson


class LocationsDbJson(private val context: Context) {

    val locationsData: Map<Int, LocationJson> by lazy {
        val jsonString = getJsonFromRawResource(context, R.raw.locations)
        Json.decodeFromString(jsonString)
    }

    val bookingIdsData: Map<Int, Int> by lazy {
        val jsonString = getJsonFromRawResource(context, R.raw.booking_ids)
        Json.decodeFromString(jsonString)
    }

    val kiwiCityIdData: List<Map<Int, List<String?>>> by lazy {
        val jsonString = getJsonFromRawResource(context, R.raw.kiwi_city_id)
        Json.decodeFromString(jsonString)
    }

    private fun getJsonFromRawResource(context: Context, resourceId: Int): String {
        return context.resources.openRawResource(resourceId).bufferedReader().use {
            it.readText()
        }
    }

}
