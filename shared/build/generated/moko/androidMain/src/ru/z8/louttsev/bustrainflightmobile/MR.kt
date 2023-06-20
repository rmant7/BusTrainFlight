package com.travelapp.bustrainflightmobile

import dev.icerock.moko.graphics.Color
import dev.icerock.moko.resources.ColorResource
import dev.icerock.moko.resources.FileResource
import dev.icerock.moko.resources.FontResource
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.PluralsResource
import dev.icerock.moko.resources.ResourceContainer
import dev.icerock.moko.resources.StringResource
import com.travelapp.bustrainflightmobile.shared.R

actual object MR {
  actual object strings : ResourceContainer<StringResource> {
    actual val app_name: StringResource = StringResource(R.string.app_name)

    actual val action_bar_tagline: StringResource = StringResource(R.string.action_bar_tagline)

    actual val transportation_type_flight: StringResource =
        StringResource(R.string.transportation_type_flight)

    actual val transportation_type_bus: StringResource =
        StringResource(R.string.transportation_type_bus)

    actual val transportation_type_train: StringResource =
        StringResource(R.string.transportation_type_train)

    actual val transportation_type_car_drive: StringResource =
        StringResource(R.string.transportation_type_car_drive)

    actual val transportation_type_taxi: StringResource =
        StringResource(R.string.transportation_type_taxi)

    actual val transportation_type_walk: StringResource =
        StringResource(R.string.transportation_type_walk)

    actual val transportation_type_town_car: StringResource =
        StringResource(R.string.transportation_type_town_car)

    actual val transportation_type_ride_share: StringResource =
        StringResource(R.string.transportation_type_ride_share)

    actual val transportation_type_shuttle: StringResource =
        StringResource(R.string.transportation_type_shuttle)

    actual val transportation_type_ferry: StringResource =
        StringResource(R.string.transportation_type_ferry)

    actual val transportation_type_subway: StringResource =
        StringResource(R.string.transportation_type_subway)

    actual val transportation_type_undefined: StringResource =
        StringResource(R.string.transportation_type_undefined)

    actual val route_type_ground: StringResource = StringResource(R.string.route_type_ground)

    actual val route_type_mixed: StringResource = StringResource(R.string.route_type_mixed)

    actual val route_type_flying: StringResource = StringResource(R.string.route_type_flying)

    actual val route_type_fixed_without_ride_share: StringResource =
        StringResource(R.string.route_type_fixed_without_ride_share)

    actual val route_type_without_ride_share: StringResource =
        StringResource(R.string.route_type_without_ride_share)

    actual val route_type_direct: StringResource = StringResource(R.string.route_type_direct)

    actual val formatted_days_time_component: StringResource =
        StringResource(R.string.formatted_days_time_component)

    actual val formatted_hours_time_component: StringResource =
        StringResource(R.string.formatted_hours_time_component)

    actual val formatted_minutes_time_component: StringResource =
        StringResource(R.string.formatted_minutes_time_component)

    actual val from_location_input_field_hint: StringResource =
        StringResource(R.string.from_location_input_field_hint)

    actual val to_location_input_field_hint: StringResource =
        StringResource(R.string.to_location_input_field_hint)

    actual val clear_button_text: StringResource = StringResource(R.string.clear_button_text)

    actual val go_button_text: StringResource = StringResource(R.string.go_button_text)

    actual val no_data_error_message: StringResource =
        StringResource(R.string.no_data_error_message)

    actual val invalid_selection_error_message: StringResource =
        StringResource(R.string.invalid_selection_error_message)

    actual val not_allowable_character_error_message: StringResource =
        StringResource(R.string.not_allowable_character_error_message)

    actual val action_button_text: StringResource = StringResource(R.string.action_button_text)

    actual val clause_duration_included_airport_transfer_text: StringResource =
        StringResource(R.string.clause_duration_included_airport_transfer_text)
  }

  actual object plurals : ResourceContainer<PluralsResource>

  actual object images : ResourceContainer<ImageResource> {
    actual val ic_bus: ImageResource = ImageResource(R.drawable.ic_bus)

    actual val ic_car_drive: ImageResource = ImageResource(R.drawable.ic_car_drive)

    actual val ic_clear: ImageResource = ImageResource(R.drawable.ic_clear)

    actual val ic_ferry: ImageResource = ImageResource(R.drawable.ic_ferry)

    actual val ic_plane: ImageResource = ImageResource(R.drawable.ic_plane)

    actual val ic_ride_share: ImageResource = ImageResource(R.drawable.ic_ride_share)

    actual val ic_shuttle: ImageResource = ImageResource(R.drawable.ic_shuttle)

    actual val ic_subway: ImageResource = ImageResource(R.drawable.ic_subway)

    actual val ic_taxi: ImageResource = ImageResource(R.drawable.ic_taxi)

    actual val ic_town_car: ImageResource = ImageResource(R.drawable.ic_town_car)

    actual val ic_train: ImageResource = ImageResource(R.drawable.ic_train)

    actual val ic_undefined: ImageResource = ImageResource(R.drawable.ic_undefined)

    actual val ic_walk: ImageResource = ImageResource(R.drawable.ic_walk)
  }

  actual object fonts : ResourceContainer<FontResource>

  actual object files : ResourceContainer<FileResource> {
    actual val booking_ids: FileResource = FileResource(rawResId = R.raw.booking_ids)
  }

  actual object colors : ResourceContainer<ColorResource>
}
