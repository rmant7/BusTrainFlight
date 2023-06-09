/**
 * CheapTrip Mobile
 * This is mobile client for LowCostsTrip server.
 */
package com.travelapp.bustrainflightmobile.androidApp.model.data

/**
 * Declares concrete location (city).
 *
 * @property id Location ID.
 * @property name Location name.
 */
data class Location(
    val id: Int,
    val name: String,
    val country: String
) {
    override fun toString(): String {
        return name
    }

    /**
     * Declares location type in relation to route.
     */
    enum class Type {
        ALL, FROM, TO
    }

    fun getLocation() = "$name, $country"
}
