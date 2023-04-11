package ru.z8.louttsev.cheaptripmobile.shared.infrastructure.persistence

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ru.z8.louttsev.cheaptripmobile.shared.ResourceReader
import ru.z8.louttsev.cheaptripmobile.shared.model.data.LocationJson


class LocationsDbJson {

    private val locationsData: Map<Int, LocationJson> by lazy {
        val jsonString = ResourceReader().readResource("MR/files/locations.json")
        Json.decodeFromString<Map<Int, LocationJson>>(jsonString)
    }

    fun getLocations() = locationsData

}
