package com.travelapp.bustrainflightmobile.shared.infrastructure.persistence

import kotlin.Float
import kotlin.Int
import kotlin.Long
import kotlin.String

data class Path(
  val transportation_type_name: String,
  val price: Float,
  val duration: Int,
  val from_location_name: String,
  val to_location_name: String,
  val route_id: Long
) {
  override fun toString(): String = """
  |Path [
  |  transportation_type_name: $transportation_type_name
  |  price: $price
  |  duration: $duration
  |  from_location_name: $from_location_name
  |  to_location_name: $to_location_name
  |  route_id: $route_id
  |]
  """.trimMargin()
}
