package ru.z8.louttsev.bustrainflightmobile.shared.infrastructure.datasource

import kotlin.Int
import kotlin.String

data class SelectLocationsByName(
  val id: Int,
  val name: String,
  val name_ru: String
) {
  override fun toString(): String = """
  |SelectLocationsByName [
  |  id: $id
  |  name: $name
  |  name_ru: $name_ru
  |]
  """.trimMargin()
}
