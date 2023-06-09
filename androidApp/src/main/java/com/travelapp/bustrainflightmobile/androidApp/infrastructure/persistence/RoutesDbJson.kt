package com.travelapp.bustrainflightmobile.androidApp.infrastructure.persistence

import android.content.Context
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import com.travelapp.bustrainflightmobile.androidApp.R
import com.travelapp.bustrainflightmobile.androidApp.model.data.DirectRouteJson
import com.travelapp.bustrainflightmobile.androidApp.model.data.RouteJson
import com.travelapp.bustrainflightmobile.androidApp.model.data.Transport

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