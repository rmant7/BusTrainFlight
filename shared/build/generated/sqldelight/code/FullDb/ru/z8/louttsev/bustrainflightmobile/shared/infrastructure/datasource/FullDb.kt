package com.travelapp.bustrainflightmobile.shared.infrastructure.datasource

import com.squareup.sqldelight.Transacter
import com.squareup.sqldelight.db.SqlDriver
import com.travelapp.bustrainflightmobile.shared.infrastructure.datasource.shared.newInstance
import com.travelapp.bustrainflightmobile.shared.infrastructure.datasource.shared.schema

interface FullDb : Transacter {
  val fullDbQueries: FullDbQueries

  companion object {
    val Schema: SqlDriver.Schema
      get() = FullDb::class.schema

    operator fun invoke(driver: SqlDriver): FullDb = FullDb::class.newInstance(driver)}
}
