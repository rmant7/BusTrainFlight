package ru.z8.louttsev.bustrainflightmobile.shared.infrastructure.persistence

import kotlin.Int
import kotlin.String

data class SelectLocationsByName(
  val id: Int,
  val name: String
) {
  override fun toString(): String = """
  |SelectLocationsByName [
  |  id: $id
  |  name: $name
  |]
  """.trimMargin()
}
