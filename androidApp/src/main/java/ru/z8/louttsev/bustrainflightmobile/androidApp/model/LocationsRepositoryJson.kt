package ru.z8.louttsev.bustrainflightmobile.androidApp.model

import io.github.aakira.napier.Napier
import ru.z8.louttsev.bustrainflightmobile.androidApp.currentLocale
import ru.z8.louttsev.bustrainflightmobile.androidApp.infrastructure.persistence.LocationsDbJson
import ru.z8.louttsev.bustrainflightmobile.androidApp.model.data.Locale
import ru.z8.louttsev.bustrainflightmobile.androidApp.model.data.Location
import ru.z8.louttsev.bustrainflightmobile.androidApp.model.data.LocationJson

class LocationsRepositoryJson (val db: LocationsDbJson) {

    private val locations: Map<Int, LocationJson> = db.getLocations()

    private val bookingIds: Map<Int, Int> = db.getBookingIds()

    fun searchLocationsByName(
        needle: String,
        type: Location.Type = Location.Type.ALL,
        limit: Int = 10,
        locale: Locale = currentLocale
    ): List<Location> {
        val locationList = mutableListOf<Location>()
        for((id, location) in locations.entries){
            if (location.name.startsWith(needle, ignoreCase = true)){
                locationList.add(Location(id, location.name))
                if (locationList.size == limit)
                    break
            }
        }

        if (locationList.size < limit) {
            for((id, location) in locations.entries){
                if (location.name.contains(needle, ignoreCase = true)){
                    if (!locationList.contains(Location(id, location.name)))
                        locationList.add(Location(id, location.name))
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
                return Location(locationId, location.name)
            }
        }
        return null
    }

    fun getBookingId(locationId: Int) = bookingIds[locationId]
}