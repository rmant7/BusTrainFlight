package ru.z8.louttsev.bustrainflightmobile.shared.infrastructure.datasource

import kotlin.Int
import kotlin.String

data class Transportation_types(
  val id: Int,
  val name: String
) {
  override fun toString(): String = """
  |Transportation_types [
  |  id: $id
  |  name: $name
  |]
  """.trimMargin()
}
