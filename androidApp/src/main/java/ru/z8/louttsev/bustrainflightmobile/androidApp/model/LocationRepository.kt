package ru.z8.louttsev.bustrainflightmobile.androidApp.model

import ru.z8.louttsev.bustrainflightmobile.androidApp.currentLocale
import ru.z8.louttsev.bustrainflightmobile.androidApp.infrastructure.persistence.LocationsDbJson
import ru.z8.louttsev.bustrainflightmobile.androidApp.model.data.Locale
import ru.z8.louttsev.bustrainflightmobile.androidApp.model.data.LocationData
import ru.z8.louttsev.bustrainflightmobile.androidApp.model.data.LocationJson
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


    //"name": "Orlando",
    //"latitude": 28.54210090637207,
    //"longitude": -81.37899780273438,

    //"name": "Buenos Aires",
    //"latitude": -34.607601165771484,
    //"longitude": -58.43709945678711,
    fun searchLocation(latitude: Double, longitude: Double): LocationData? {
        val cityOne = searchLocationLatitude(-34.607601165771484)//(latitude)
        val cityTwo = searchLocationLongitude(-58.43709945678711)//(longitude)
        if (cityOne != null && cityTwo != null) {
            return if
                           (sqrt(
                    abs(latitude - cityOne.latitude).pow(2) + abs(longitude - cityOne.longitude).pow(
                        2
                    )
                ) >
                sqrt(
                    abs(latitude - cityTwo.latitude).pow(2) + abs(longitude - cityTwo.longitude).pow(
                        2
                    )
                )
            ) {
                searchLocationByName(cityTwo.name)
            } else searchLocationByName(cityOne.name)
        }
        return null
    }

    private fun searchLocationByName(name: String): LocationData? {
        for ((locationId, location) in locations.entries) {
            if (name == location.name) return searchLocationById(locationId)
        }
        return null
    }

    private fun searchLocationLatitude(latitude: Double): LocationJson? {
        var minLatitude = Double.MAX_VALUE
        var minlocation: LocationJson? = null
        for ((_, location) in locations.entries) {
            if (abs(latitude - location.latitude) < minLatitude) {
                minLatitude = location.latitude
                minlocation = location
            }
        }
        return minlocation
    }

    private fun searchLocationLongitude(longitude: Double): LocationJson? {
        var minLongitude = Double.MAX_VALUE
        var minlocation: LocationJson? = null
        for ((_, location) in locations.entries) {
            if (abs(longitude - location.longitude) < minLongitude) {
                minLongitude = location.longitude
                minlocation = location
            }
        }
        return minlocation
    }

    fun getBookingId(locationId: Int) = bookingIds[locationId]
}