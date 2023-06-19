package ru.z8.louttsev.bustrainflightmobile.shared.infrastructure.datasource

import kotlin.Float
import kotlin.Int
import kotlin.String

data class Currencies(
  val id: Int,
  val name: String,
  val code: String,
  val symbol: String,
  val one_euro_rate: Float?,
  val r2r_symbol: String?
) {
  override fun toString(): String = """
  |Currencies [
  |  id: $id
  |  name: $name
  |  code: $code
  |  symbol: $symbol
  |  one_euro_rate: $one_euro_rate
  |  r2r_symbol: $r2r_symbol
  |]
  """.trimMargin()
}
