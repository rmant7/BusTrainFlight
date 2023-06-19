package ru.z8.louttsev.bustrainflightmobile.shared.infrastructure.persistence

import kotlin.Float
import kotlin.Int
import kotlin.Long
import kotlin.String

data class Route(
  val id: Long,
  val type_name: String,
  val price: Float,
  val duration: Int,
  val from_location_name: String,
  val to_location_name: String,
  val language_code: String
) {
  override fun toString(): String = """
  |Route [
  |  id: $id
  |  type_name: $type_name
  |  price: $price
  |  duration: $duration
  |  from_location_name: $from_location_name
  |  to_location_name: $to_location_name
  |  language_code: $language_code
  |]
  """.trimMargin()
}
