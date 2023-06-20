package com.travelapp.bustrainflightmobile.shared.infrastructure.persistence

import com.squareup.sqldelight.Transacter
import com.squareup.sqldelight.db.SqlDriver
import com.travelapp.bustrainflightmobile.shared.infrastructure.persistence.shared.newInstance
import com.travelapp.bustrainflightmobile.shared.infrastructure.persistence.shared.schema

interface LocalDb : Transacter {
  val localDbQueries: LocalDbQueries

  companion object {
    val Schema: SqlDriver.Schema
      get() = LocalDb::class.schema

    operator fun invoke(driver: SqlDriver): LocalDb = LocalDb::class.newInstance(driver)}
}
