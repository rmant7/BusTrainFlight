package ru.z8.louttsev.bustrainflightmobile.shared.infrastructure.persistence

import kotlin.Int
import kotlin.String

data class Location(
  val id: Int,
  val name: String,
  val type_name: String,
  val language_code: String
) {
  override fun toString(): String = """
  |Location [
  |  id: $id
  |  name: $name
  |  type_name: $type_name
  |  language_code: $language_code
  |]
  """.trimMargin()
}
