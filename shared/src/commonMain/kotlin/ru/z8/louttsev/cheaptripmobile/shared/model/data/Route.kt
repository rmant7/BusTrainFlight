/**
 * CheapTrip Mobile
 * This is mobile client for LowCostsTrip server.
 */
package ru.z8.louttsev.cheaptripmobile.shared.model.data

import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.StringDesc
import ru.z8.louttsev.cheaptripmobile.MR
import ru.z8.louttsev.cheaptripmobile.shared.convertToString

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
) {
    /**
     * Declares route type in relation to ways of moving.
     *
     * @property value String representation for JSON conversion
     */
    enum class Type(val value: String, private val stringResourceId: StringResource) {
        GROUND("ground_routes", MR.strings.route_type_ground),
        MIXED("mixed_routes", MR.strings.route_type_mixed),
        FLYING("flying_routes", MR.strings.route_type_flying),
        FIXED_WITHOUT_RIDE_SHARE(
            "fixed_routes_without_ride_share",
            MR.strings.route_type_fixed_without_ride_share
        ),
        WITHOUT_RIDE_SHARE("routes_without_ride_share", MR.strings.route_type_without_ride_share),
        DIRECT("direct_routes", MR.strings.route_type_direct);

        override fun toString(): String {
            return StringDesc.Resource(stringResourceId).convertToString()
        }
    }

    private val pointsDelimiter = "\u2009\u2794\u2009"

    /**
     * String representation of route plan, eg. 'Muscat → Toronto → Abu Dhabi'
     */
    fun getRoutePlan() =
        directPaths.joinToString(
            pointsDelimiter,
            transform = Path::from
        ) + pointsDelimiter + directPaths.last().to

    /**
     * String representation of total route duration, eg. '2 d 11 h 27 m'
     */
    fun getRouteDuration() =
        DurationConverter.minutesToTimeComponents(durationMinutes)
}
