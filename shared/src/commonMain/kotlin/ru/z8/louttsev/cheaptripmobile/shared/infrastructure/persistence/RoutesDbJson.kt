package ru.z8.louttsev.cheaptripmobile.shared.infrastructure.persistence

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ru.z8.louttsev.cheaptripmobile.shared.ResourceReader
import ru.z8.louttsev.cheaptripmobile.shared.model.data.DirectRouteJson
import ru.z8.louttsev.cheaptripmobile.shared.model.data.Transport

class RoutesDbJson {

    private val directRoutesData: Map<Int, DirectRouteJson> by lazy {
        val jsonRoutesString = ResourceReader().readResource("MR/files/direct_routes.json")
        Json.decodeFromString<Map<Int, DirectRouteJson>>(jsonRoutesString)
    }

    fun getDirectRoutes() = directRoutesData

    private val transportData: Map<Int, Transport> by lazy {
        val jsonTransportString = ResourceReader().readResource("MR/files/transport.json")
        Json.decodeFromString<Map<Int, Transport>>(jsonTransportString)
    }

    fun getTransport() = transportData

}