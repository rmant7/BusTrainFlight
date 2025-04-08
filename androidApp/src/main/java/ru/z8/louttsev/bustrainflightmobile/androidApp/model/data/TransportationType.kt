/**
 * CheapTrip Mobile
 * This is mobile client for LowCostsTrip server.
 */
package ru.z8.louttsev.bustrainflightmobile.androidApp.model.data

import ru.z8.louttsev.bustrainflightmobile.androidApp.R

/**
 * Declares path type in relation to transport.
 *
 * @property value String representation for JSON conversion
 * @param stringResourceId Resource ID for string representation into UI
 * @property imageResource Resource ID for image representation into UI
 */
enum class TransportationType(
    val id: Int,
    val value: String,
    val stringResourceId: Int,
    val imageResource: Int
) {
    FLIGHT(0,"Flight", R.string.transportation_type_flight, R.drawable.ic_plane),
    BUS(1,"Bus", R.string.transportation_type_bus, R.drawable.ic_bus),
    TRAIN(2,"Train", R.string.transportation_type_train, R.drawable.ic_train),
    CAR_DRIVE(3,"Car Drive", R.string.transportation_type_car_drive, R.drawable.ic_car_drive),
    TAXI(4,"Taxi", R.string.transportation_type_taxi, R.drawable.ic_taxi),
    WALK(5,"Walk", R.string.transportation_type_walk, R.drawable.ic_walk),
    TOWN_CAR(6,"Town Car", R.string.transportation_type_town_car, R.drawable.ic_town_car),
    RIDE_SHARE(7,"Ride Share", R.string.transportation_type_ride_share, R.drawable.ic_ride_share),
    SHUTTLE(8,"Shuttle", R.string.transportation_type_shuttle, R.drawable.ic_shuttle),
    FERRY(9,"Ferry", R.string.transportation_type_ferry, R.drawable.ic_ferry),
    SUBWAY(10,"Subway", R.string.transportation_type_subway, R.drawable.ic_subway),
    UNDEFINED(11,"Undefined", R.string.transportation_type_undefined, R.drawable.ic_undefined);

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
        infix fun fromValue(value: String?) = entries.find { it.value == value } ?: UNDEFINED
    }
}
