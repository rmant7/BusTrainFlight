/**
 * CheapTrip Mobile
 * This is mobile client for LowCostsTrip server.
 */
package ru.z8.louttsev.bustrainflightmobile.androidApp.model.data

import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject
import ru.z8.louttsev.bustrainflightmobile.androidApp.R

/**
 * Declares aggregate route between selected locations.
 *
 * @property routeType Route type in relation to ways of moving.
 * @property euroPrice Total route cost in EUR currency.
 * @property durationMinutes Total route duration in minutes.
 * @property directPaths Particular sections (paths) within aggregate route.
 */
data class Route(
    val routeType: Type,
    val euroPrice: Float,
    val durationMinutes: Int,
    val directPaths: List<Path>
): KoinComponent {
    /**
     * Declares route type in relation to ways of moving.
     *
     * @property value String representation for JSON conversion
     */
    private val durationConverter: DurationConverter by inject()

    enum class Type(val value: String, private val stringResourceId: Int) {
        GROUND("ground_routes", R.string.route_type_ground),
        MIXED("mixed_routes", R.string.route_type_mixed),
        FLYING("flying_routes", R.string.route_type_flying),
        FIXED_WITHOUT_RIDE_SHARE(
            "fixed_routes_without_ride_share",
            R.string.route_type_fixed_without_ride_share
        ),
        WITHOUT_RIDE_SHARE("routes_without_ride_share", R.string.route_type_without_ride_share),
        DIRECT("direct_routes", R.string.route_type_direct);

        override fun toString(): String {
            return value
        }
    }

    private val pointsDelimiter = " \u2192 "

    /**
     * String representation of route plan, eg. 'Muscat → Toronto → Abu Dhabi'
     */
    fun getRoutePlan() =
        directPaths.joinToString(
            pointsDelimiter,
            transform = {path -> path.from.name}
        ) + pointsDelimiter + directPaths.last().to.name

    /**
     * String representation of total route duration, eg. '2 d 11 h 27 m'
     */
    fun getRouteDuration() =
        durationConverter.minutesToTimeComponents(durationMinutes)

    override fun equals(other: Any?): Boolean {
        return if (other !is Route){
            super.equals(other)
        } else {
            this.directPaths == other.directPaths
        }
    }
}
