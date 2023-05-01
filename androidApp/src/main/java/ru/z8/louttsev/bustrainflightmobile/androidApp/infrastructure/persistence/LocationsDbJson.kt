package ru.z8.louttsev.bustrainflightmobile.androidApp.infrastructure.persistence

import android.content.Context
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ru.z8.louttsev.bustrainflightmobile.androidApp.R
import ru.z8.louttsev.bustrainflightmobile.androidApp.model.data.LocationJson


class LocationsDbJson(private val context: Context) {

    private val locationsData: Map<Int, LocationJson> by lazy {
        val jsonString = getJsonFromRawResource(context, R.raw.locations)
        Json.decodeFromString(jsonString)
    }

    fun getLocations() = locationsData

    private val bookingIdsData: Map<Int, Int> by lazy {
        val jsonString = getJsonFromRawResource(context, R.raw.booking_ids)
        Json.decodeFromString(jsonString)
    }

    fun getBookingIds() = bookingIdsData

    private fun getJsonFromRawResource(context: Context, resourceId: Int): String {
        return context.resources.openRawResource(resourceId).bufferedReader().use {
            it.readText()
        }
    }

}
