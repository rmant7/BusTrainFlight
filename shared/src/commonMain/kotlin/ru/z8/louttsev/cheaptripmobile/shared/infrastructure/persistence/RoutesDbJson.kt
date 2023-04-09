package ru.z8.louttsev.cheaptripmobile.shared.infrastructure.persistence

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ru.z8.louttsev.cheaptripmobile.shared.ResourceReader
import ru.z8.louttsev.cheaptripmobile.shared.model.data.DirectRouteJson
import ru.z8.louttsev.cheaptripmobile.shared.model.data.Transport
import ru.z8.louttsev.cheaptripmobile.shared.model.data.TransportationType

object RoutesDbJson {

    val directRoutesData: Map<Int, DirectRouteJson> by lazy {
        val jsonString = ResourceReader().readResource("MR/files/direct_routes.json")
        Json.decodeFromString<Map<Int, DirectRouteJson>>(jsonString)
    }

    val transport: Map<Int, Transport> by lazy {
        val jsonString = ResourceReader().readResource("MR/files/transport.json")
        Json.decodeFromString<Map<Int, Transport>>(jsonString)
    }

}