package ru.z8.louttsev.cheaptripmobile.shared.infrastructure.persistence

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import ru.z8.louttsev.cheaptripmobile.shared.ResourceReader
import ru.z8.louttsev.cheaptripmobile.shared.model.data.DirectRouteJson
import ru.z8.louttsev.cheaptripmobile.shared.model.data.RouteJson
import ru.z8.louttsev.cheaptripmobile.shared.model.data.Transport

class RoutesDbJson {

    private val directRoutesData: Map<Int, DirectRouteJson> by lazy {
        val jsonRoutesString = ResourceReader().readResource("MR/files/direct_routes.json")
        Json.decodeFromString<Map<Int, DirectRouteJson>>(jsonRoutesString)
    }

    fun getDirectRoutes() = directRoutesData

    private val flyingRoutesData: Map<Int, RouteJson> by lazy {
        val jsonRoutesString = ResourceReader().readResource("MR/files/flying_routes.json")
        Json.decodeFromString<Map<Int, RouteJson>>(jsonRoutesString)
    }

    fun getFlyingRoutes() = flyingRoutesData

    private val fixedRoutesData: Map<Int, RouteJson> by lazy {
        val jsonRoutesString = ResourceReader().readResource("MR/files/fixed_routes.json")
        Json.decodeFromString<Map<Int, RouteJson>>(jsonRoutesString)
    }

    fun getFixedRoutes() = fixedRoutesData


    private val mixedRoutesData: Map<Int, RouteJson> by lazy {
        val jsonRoutesString = ResourceReader().readResource("MR/files/routes.json")
        Json.decodeFromString<Map<Int, RouteJson>>(jsonRoutesString)
    }

    fun getMixedRoutes() = mixedRoutesData

    private val transportData: Map<Int, Transport> by lazy {
        val jsonTransportString = ResourceReader().readResource("MR/files/transport.json")
        Json.decodeFromString<Map<Int, Transport>>(jsonTransportString)
    }

    fun getTransport() = transportData
}

object IntListSerializer : KSerializer<List<Int>> {

    override val descriptor: SerialDescriptor =
        ListSerializer(Int.serializer()).descriptor

    override fun serialize(encoder: Encoder, value: List<Int>) {
        encoder.encodeString(value.joinToString(","))
    }

    override fun deserialize(decoder: Decoder): List<Int> {
        return decoder.decodeString().split(",").map { it.toInt() }
    }
}