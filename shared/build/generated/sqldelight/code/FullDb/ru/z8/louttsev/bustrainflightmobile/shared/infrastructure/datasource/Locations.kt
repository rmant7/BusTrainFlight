package ru.z8.louttsev.bustrainflightmobile.shared.infrastructure.datasource

import kotlin.Float
import kotlin.Int
import kotlin.String

data class Locations(
  val id: Int,
  val name: String,
  val country_id: Int,
  val latitude: Float?,
  val longitude: Float?,
  val name_ru: String
) {
  override fun toString(): String = """
  |Locations [
  |  id: $id
  |  name: $name
  |  country_id: $country_id
  |  latitude: $latitude
  |  longitude: $longitude
  |  name_ru: $name_ru
  |]
  """.trimMargin()
}
