package com.travelapp.bustrainflightmobile.shared.infrastructure.datasource

import kotlin.Int
import kotlin.String

data class Countries(
  val country_name: String?,
  val country_id: Int,
  val country_name_ru: String?
) {
  override fun toString(): String = """
  |Countries [
  |  country_name: $country_name
  |  country_id: $country_id
  |  country_name_ru: $country_name_ru
  |]
  """.trimMargin()
}
