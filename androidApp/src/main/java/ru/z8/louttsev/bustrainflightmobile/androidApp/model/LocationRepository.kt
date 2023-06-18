package ru.z8.louttsev.bustrainflightmobile.androidApp.model

import ru.z8.louttsev.bustrainflightmobile.androidApp.currentLocale
import ru.z8.louttsev.bustrainflightmobile.androidApp.infrastructure.persistence.LocationsDbJson
import ru.z8.louttsev.bustrainflightmobile.androidApp.model.data.Locale
import ru.z8.louttsev.bustrainflightmobile.androidApp.model.data.Location
import ru.z8.louttsev.bustrainflightmobile.androidApp.model.data.LocationJson

class LocationRepository (val db: LocationsDbJson) {

    val locations: Map<Int, LocationJson> = db.locationsData

    private val bookingIds: Map<Int, Int> = db.bookingIdsData

    fun searchLocationsByName(
        needle: String,
        type: Location.Type = Location.Type.ALL,
        limit: Int = 10,
        locale: Locale = currentLocale
    ): List<Location> {
        if (needle.isEmpty()){
            return listOf(Location(0, "Anywhere", ""))
        }
        val locationList = mutableListOf<Location>()
        for((id, location) in locations.entries){
            if (location.name.startsWith(needle, ignoreCase = true)){
                locationList.add(Location(id, location.name, location.countryName))
                if (locationList.size == limit)
                    break
            }
        }

        if (locationList.size < limit) {
            for((id, location) in locations.entries){
                if (location.name.contains(needle, ignoreCase = true)){
                    if (!locationList.contains(Location(id, location.name, location.countryName)))
                        locationList.add(Location(id, location.name, location.countryName))
                        if (locationList.size == limit)
                            break
                }
            }
        }
        return locationList
    }

    fun searchLocationById(id: Int): Location?{
        for((locationId, location) in locations.entries){
            if (locationId == id){
                return Location(locationId, location.name, location.countryName)
            }
        }
        return null
    }

    fun getBookingId(locationId: Int) = bookingIds[locationId]
}