package com.travelapp.bustrainflightmobile.shared.infrastructure.datasource

import kotlin.Float
import kotlin.Int
import kotlin.String

data class Fixed_routes_without_ride_share(
  val id: Int,
  val from: Int,
  val to: Int,
  val euro_price: Float,
  val travel_data: String
) {
  override fun toString(): String = """
  |Fixed_routes_without_ride_share [
  |  id: $id
  |  from: $from
  |  to: $to
  |  euro_price: $euro_price
  |  travel_data: $travel_data
  |]
  """.trimMargin()
}
