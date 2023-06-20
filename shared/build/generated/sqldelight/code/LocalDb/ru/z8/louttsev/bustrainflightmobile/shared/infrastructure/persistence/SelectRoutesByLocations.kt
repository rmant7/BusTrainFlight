package com.travelapp.bustrainflightmobile.shared.infrastructure.persistence

import kotlin.Float
import kotlin.Int
import kotlin.Long
import kotlin.String

data class SelectRoutesByLocations(
  val id: Long,
  val type_name: String,
  val price: Float,
  val duration: Int
) {
  override fun toString(): String = """
  |SelectRoutesByLocations [
  |  id: $id
  |  type_name: $type_name
  |  price: $price
  |  duration: $duration
  |]
  """.trimMargin()
}
