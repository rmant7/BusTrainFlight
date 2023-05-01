package ru.z8.louttsev.bustrainflightmobile.androidApp.infrastructure.persistence

import android.content.Context
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import ru.z8.louttsev.bustrainflightmobile.androidApp.R
import ru.z8.louttsev.bustrainflightmobile.androidApp.model.data.DirectRouteJson
import ru.z8.louttsev.bustrainflightmobile.androidApp.model.data.RouteJson
import ru.z8.louttsev.bustrainflightmobile.androidApp.model.data.Transport

class RoutesDbJson(private val context: Context) {

    private val directRoutesData: Map<Int, DirectRouteJson> by lazy {
        val jsonRoutesString = getJsonFromRawResource(context, R.raw.direct_routes)
        Json.decodeFromString(jsonRoutesString)
    }

    fun getDirectRoutes() = directRoutesData

    private val flyingRoutesData: Map<Int, RouteJson> by lazy {
        val jsonRoutesString = getJsonFromRawResource(context, R.raw.flying_routes)
        Json.decodeFromString(jsonRoutesString)
    }

    fun getFlyingRoutes() = flyingRoutesData

    private val fixedRoutesData: Map<Int, RouteJson> by lazy {
        val jsonRoutesString = getJsonFromRawResource(context, R.raw.fixed_routes)
        Json.decodeFromString(jsonRoutesString)
    }

    fun getFixedRoutes() = fixedRoutesData


    private val mixedRoutesData: Map<Int, RouteJson> by lazy {
        val jsonRoutesString = getJsonFromRawResource(context, R.raw.routes)
        Json.decodeFromString(jsonRoutesString)
    }

    fun getMixedRoutes() = mixedRoutesData

    private val transportData: Map<Int, Transport> by lazy {
        val jsonTransportString = getJsonFromRawResource(context, R.raw.transport)
        Json.decodeFromString(jsonTransportString)
    }

    fun getTransport() = transportData

    private fun getJsonFromRawResource(context: Context, resourceId: Int): String {
        return context.resources.openRawResource(resourceId).bufferedReader().use {
            it.readText()
        }
    }

}