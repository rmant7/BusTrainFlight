package ru.z8.louttsev.bustrainflightmobile.shared.infrastructure.datasource

import kotlin.String

data class SelectLocationNamesById(
  val name: String,
  val name_ru: String
) {
  override fun toString(): String = """
  |SelectLocationNamesById [
  |  name: $name
  |  name_ru: $name_ru
  |]
  """.trimMargin()
}
