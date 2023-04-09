package ru.z8.louttsev.cheaptripmobile.shared.infrastructure.persistence

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ru.z8.louttsev.cheaptripmobile.shared.ResourceReader
import ru.z8.louttsev.cheaptripmobile.shared.model.data.LocationJson


object LocationsDbJson {

//    private val jsonPath = "shared/src/commonMain/resources/MR/files/locations.json"

    val locationsData: Map<Int, LocationJson> by lazy {
        val jsonString = ResourceReader().readResource("MR/files/locations.json")
        Json.decodeFromString<Map<Int, LocationJson>>(jsonString)
    }

//        val jsonString = StringDesc.Raw("raw/locations.json")

//        val locationsJson = FileUtils.readTextFile(jsonString)
//        val jsonString = Resource.Companion.readRaw("raw/data.json")
//        Json.decodeFromString<LocationMapJson>(jsonString.toString())
    }
