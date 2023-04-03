/**
 * CheapTrip Mobile
 * This is mobile client for LowCostsTrip server.
 */
package ru.z8.louttsev.cheaptripmobile.shared.model.data


import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.StringDesc
import ru.z8.louttsev.cheaptripmobile.MR
import ru.z8.louttsev.cheaptripmobile.shared.convertToString

/**
 * Declares path type in relation to transport.
 *
 * @property value String representation for JSON conversion
 * @param stringResourceId Resource ID for string representation into UI
 * @property imageResource Resource ID for image representation into UI
 */
enum class TransportationType(
    val value: String,
    private val stringResourceId: StringResource,
    val imageResource: ImageResource
) {
    FLIGHT("Flight", MR.strings.transportation_type_flight, MR.images.ic_plane),
    BUS("Bus", MR.strings.transportation_type_bus, MR.images.ic_bus),
    TRAIN("Train", MR.strings.transportation_type_train, MR.images.ic_train),
    CAR_DRIVE("Car Drive", MR.strings.transportation_type_car_drive, MR.images.ic_car_drive),
    TAXI("Taxi", MR.strings.transportation_type_taxi, MR.images.ic_taxi),
    WALK("Walk", MR.strings.transportation_type_walk, MR.images.ic_walk),
    TOWN_CAR("Town Car", MR.strings.transportation_type_town_car, MR.images.ic_town_car),
    RIDE_SHARE("Ride Share", MR.strings.transportation_type_ride_share, MR.images.ic_ride_share),
    SHUTTLE("Shuttle", MR.strings.transportation_type_shuttle, MR.images.ic_shuttle),
    FERRY("Ferry", MR.strings.transportation_type_ferry, MR.images.ic_ferry),
    SUBWAY("Subway", MR.strings.transportation_type_subway, MR.images.ic_subway),
    UNDEFINED("Undefined", MR.strings.transportation_type_undefined, MR.images.ic_undefined);

    override fun toString(): String {
        return StringDesc.Resource(stringResourceId).convertToString()
    }

    companion object {
        /**
         * Gives the transportation type by its value.
         *
         * @param value Value of type
         * @return Appropriate transportation type
         */
        fun fromValue(value: String) = values().find { it.value == value } ?: UNDEFINED
    }
}
