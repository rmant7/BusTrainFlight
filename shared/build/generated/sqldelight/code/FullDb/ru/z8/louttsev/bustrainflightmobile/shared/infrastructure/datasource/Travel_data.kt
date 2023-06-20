package com.travelapp.bustrainflightmobile.shared.infrastructure.datasource

import kotlin.Float
import kotlin.Int
import kotlin.String

data class Travel_data(
  val id: Int,
  val from: Int,
  val to: Int,
  val transportation_type: Int?,
  val euro_price: Float,
  val time_in_minutes: Int?
) {
  override fun toString(): String = """
  |Travel_data [
  |  id: $id
  |  from: $from
  |  to: $to
  |  transportation_type: $transportation_type
  |  euro_price: $euro_price
  |  time_in_minutes: $time_in_minutes
  |]
  """.trimMargin()
}
