/**
 * CheapTrip Mobile
 * This is mobile client for LowCostsTrip server.
 */
package ru.z8.louttsev.cheaptripmobile.shared.model.data

/**
 * Declares particular section (path) within aggregate route.
 *
 * @property transportationType Path type in relation to transport.
 * @property euroPrice Path cost in EUR currency.
 * @property durationMinutes Path duration in minutes.
 * @property from Origin name.
 * @property to Destination name.
 */
data class Path(
    val transportationType: TransportationType,
    val euroPrice: Float,
    val durationMinutes: Int,
    val from: String,
    val to: String
) {
    private val pointsDelimiter = "\u2009\u2794\u2009"

    /**
     * String representation of path plan, eg. 'Muscat → Abu Dhabi'
     */
    fun getPathPlan() = from + pointsDelimiter + to

    /**
     * String representation of path duration, eg. '8 h 27 m'
     */
    fun getPathDuration() =
        DurationConverter.minutesToTimeComponents(durationMinutes)
}
