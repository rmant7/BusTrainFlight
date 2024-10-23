/**
 * CheapTrip Mobile
 * This is mobile client for LowCostsTrip server.
 */
package ru.z8.louttsev.bustrainflightmobile.androidApp.model.data

import javax.inject.Inject


/**
 * Declares particular section (path) within aggregate route.
 *
 * @property transportationType Path type in relation to transport.
 * @property euroPrice Path cost in EUR currency.
 * @property durationMinutes Path duration in minutes.
 * @property from Origin name.
 * @property to Destination name.
 */
data class Path @Inject constructor(
    private val durationConverter: DurationConverter,
    val transportationType: TransportationType,
    val euroPrice: Float,
    val durationMinutes: Int,
    val from: LocationData,
    val to: LocationData
) {
    private val pointsDelimiter = "\u2009\u2794\u2009"


    /**
     * String representation of path plan, eg. 'Muscat → Abu Dhabi'
     */
    fun getPathPlan() = from.name + pointsDelimiter + to.name

    /**
     * String representation of path duration, eg. '8 h 27 m'
     */
    fun getPathDuration() =
        durationConverter.minutesToTimeComponents(durationMinutes)
}
