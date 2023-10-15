/**
 * CheapTrip Mobile
 * This is mobile client for LowCostsTrip server.
 */
package ru.z8.louttsev.cheaptripmobile.androidApp.model.data

import ru.z8.louttsev.cheaptripmobile.androidApp.R

/**
 * Declares path type in relation to transport.
 *
 * @property value String representation for JSON conversion
 * @param stringResourceId Resource ID for string representation into UI
 * @property imageResource Resource ID for image representation into UI
 */
enum class TransportationType(
    val value: String,
    private val stringResourceId: Int,
    val imageResource: Int
) {
    FLIGHT("Flight", R.string.transportation_type_flight, R.drawable.ic_plane),
    BUS("Bus", R.string.transportation_type_bus, R.drawable.ic_bus),
    TRAIN("Train", R.string.transportation_type_train, R.drawable.ic_train),
    CAR_DRIVE("Car Drive", R.string.transportation_type_car_drive, R.drawable.ic_car_drive),
    TAXI("Taxi", R.string.transportation_type_taxi, R.drawable.ic_taxi),
    WALK("Walk", R.string.transportation_type_walk, R.drawable.ic_walk),
    TOWN_CAR("Town Car", R.string.transportation_type_town_car, R.drawable.ic_town_car),
    RIDE_SHARE("Ride Share", R.string.transportation_type_ride_share, R.drawable.ic_ride_share),
    SHUTTLE("Shuttle", R.string.transportation_type_shuttle, R.drawable.ic_shuttle),
    FERRY("Ferry", R.string.transportation_type_ferry, R.drawable.ic_ferry),
    SUBWAY("Subway", R.string.transportation_type_subway, R.drawable.ic_subway),
    UNDEFINED("Undefined", R.string.transportation_type_undefined, R.drawable.ic_undefined);

    override fun toString(): String {
        return value
    }

    companion object {
        /**
         * Gives the transportation type by its value.
         *
         * @param value Value of type
         * @return Appropriate transportation type
         */
        infix fun fromValue(value: String?) = values().find { it.value == value } ?: UNDEFINED
    }
}
