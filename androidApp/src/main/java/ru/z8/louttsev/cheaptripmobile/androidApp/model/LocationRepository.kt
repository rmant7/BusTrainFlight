package ru.z8.louttsev.cheaptripmobile.androidApp.model

import ru.z8.louttsev.cheaptripmobile.androidApp.currentLocale
import ru.z8.louttsev.cheaptripmobile.androidApp.infrastructure.persistence.LocationsDbJson
import ru.z8.louttsev.cheaptripmobile.androidApp.model.data.Locale
import ru.z8.louttsev.cheaptripmobile.androidApp.model.data.LocationData
import ru.z8.louttsev.cheaptripmobile.androidApp.model.data.LocationJson
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

class LocationRepository(db: LocationsDbJson) {

    private val locations: Map<Int, LocationJson> = db.locationsData

    private val bookingIds: Map<Int, Int> = db.bookingIdsData

    val kiwiCityIds: Map<Int, List<String?>> = db.kiwiCityIdData[0]

    fun searchLocationsByName(
        needle: String,
        type: LocationData.Type = LocationData.Type.ALL,
        limit: Int = 10,
        locale: Locale = currentLocale
    ): List<LocationData> {
        if (needle.isEmpty()) {
            return listOf(LocationData(0, "Anywhere", ""))
        }
        val locationList = mutableListOf<LocationData>()
        for ((id, location) in locations.entries) {
            if (location.name.startsWith(needle, ignoreCase = true)) {
                locationList.add(LocationData(id, location.name, location.countryName))
                if (locationList.size == limit)
                    break
            }
        }

        if (locationList.size < limit) {
            for ((id, location) in locations.entries) {
                if (location.name.contains(needle, ignoreCase = true)) {
                    if (!locationList.contains(
                            LocationData(
                                id,
                                location.name,
                                location.countryName
                            )
                        )
                    )
                        locationList.add(LocationData(id, location.name, location.countryName))
                    if (locationList.size == limit)
                        break
                }
            }
        }
        return locationList
    }

    fun searchLocationById(id: Int): LocationData? {
        for ((locationId, location) in locations.entries) {
            if (locationId == id) {
                return LocationData(locationId, location.name, location.countryName)
            }
        }
        return null
    }

    fun searchLocation(latitude: Double, longitude: Double): LocationData? {
        var minDistance = Double.MAX_VALUE
        var distance = 0.0
        var minlocation: LocationJson? = null
        for ((_, location) in locations.entries) {
            distance = (sqrt(abs(latitude - location.latitude).pow(2) + abs(longitude - location.longitude).pow(2)))
            if (distance < minDistance) {
                minDistance = distance
                minlocation = location
            }
        }
        if (minlocation != null) {
            return searchLocationByName(minlocation.name)
        }
        return null
    }

    private fun searchLocationByName(name: String): LocationData? {
        for ((locationId, location) in locations.entries) {
            if (name == location.name) return searchLocationById(locationId)
        }
        return null
    }

    fun getBookingId(locationId: Int) = bookingIds[locationId]
}