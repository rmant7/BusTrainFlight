package ru.z8.louttsev.cheaptripmobile.shared.model

import io.github.aakira.napier.Napier
import ru.z8.louttsev.cheaptripmobile.shared.currentLocale
import ru.z8.louttsev.cheaptripmobile.shared.infrastructure.persistence.LocationsDbJson
import ru.z8.louttsev.cheaptripmobile.shared.model.data.Locale
import ru.z8.louttsev.cheaptripmobile.shared.model.data.Location
import ru.z8.louttsev.cheaptripmobile.shared.model.data.LocationJson

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
        for(location in locations.values){
            if (location.name.startsWith(needle, ignoreCase = true)){
                locationList.add(Location(location.id, location.name))
                if (locationList.size == limit)
                    break
            }
        }

        if (locationList.size < limit) {
            for(location in locations.values){
                if (location.name.contains(needle, ignoreCase = true)){
                    locationList.add(Location(location.id, location.name))
                    if (locationList.size == limit)
                        break
                }
            }
        }
        return locationList
    }

    fun searchLocationById(id: Int): Location?{
        for(location in locations.values){
            if (location.id == id){
                return Location(location.id, location.name)
            }
        }
        return null
    }

    fun getBookingId(locationId: Int) = bookingIds[locationId]
}