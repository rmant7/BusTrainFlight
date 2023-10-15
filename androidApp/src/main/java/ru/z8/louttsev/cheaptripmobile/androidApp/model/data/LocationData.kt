/**
 * CheapTrip Mobile
 * This is mobile client for LowCostsTrip server.
 */
package ru.z8.louttsev.cheaptripmobile.androidApp.model.data

/**
 * Declares concrete location (city).
 *
 * @property id Location ID.
 * @property name Location name.
 */
data class LocationData(
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
