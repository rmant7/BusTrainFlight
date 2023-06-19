package ru.z8.louttsev.bustrainflightmobile

import dev.icerock.moko.resources.ColorResource
import dev.icerock.moko.resources.FileResource
import dev.icerock.moko.resources.FontResource
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.PluralsResource
import dev.icerock.moko.resources.ResourceContainer
import dev.icerock.moko.resources.StringResource

expect object MR {
  object strings : ResourceContainer<StringResource> {
    val app_name: StringResource

    val action_bar_tagline: StringResource

    val transportation_type_flight: StringResource

    val transportation_type_bus: StringResource

    val transportation_type_train: StringResource

    val transportation_type_car_drive: StringResource

    val transportation_type_taxi: StringResource

    val transportation_type_walk: StringResource

    val transportation_type_town_car: StringResource

    val transportation_type_ride_share: StringResource

    val transportation_type_shuttle: StringResource

    val transportation_type_ferry: StringResource

    val transportation_type_subway: StringResource

    val transportation_type_undefined: StringResource

    val route_type_ground: StringResource

    val route_type_mixed: StringResource

    val route_type_flying: StringResource

    val route_type_fixed_without_ride_share: StringResource

    val route_type_without_ride_share: StringResource

    val route_type_direct: StringResource

    val formatted_days_time_component: StringResource

    val formatted_hours_time_component: StringResource

    val formatted_minutes_time_component: StringResource

    val from_location_input_field_hint: StringResource

    val to_location_input_field_hint: StringResource

    val clear_button_text: StringResource

    val go_button_text: StringResource

    val no_data_error_message: StringResource

    val invalid_selection_error_message: StringResource

    val not_allowable_character_error_message: StringResource

    val action_button_text: StringResource

    val clause_duration_included_airport_transfer_text: StringResource
  }

  object plurals : ResourceContainer<PluralsResource>

  object images : ResourceContainer<ImageResource> {
    val ic_bus: ImageResource

    val ic_car_drive: ImageResource

    val ic_clear: ImageResource

    val ic_ferry: ImageResource

    val ic_plane: ImageResource

    val ic_ride_share: ImageResource

    val ic_shuttle: ImageResource

    val ic_subway: ImageResource

    val ic_taxi: ImageResource

    val ic_town_car: ImageResource

    val ic_train: ImageResource

    val ic_undefined: ImageResource

    val ic_walk: ImageResource
  }

  object fonts : ResourceContainer<FontResource>

  object files : ResourceContainer<FileResource> {
    val booking_ids: FileResource
  }

  object colors : ResourceContainer<ColorResource>
}
