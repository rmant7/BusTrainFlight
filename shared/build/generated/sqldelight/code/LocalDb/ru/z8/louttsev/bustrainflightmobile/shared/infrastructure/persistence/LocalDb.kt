package ru.z8.louttsev.bustrainflightmobile.shared.infrastructure.persistence

import com.squareup.sqldelight.Transacter
import com.squareup.sqldelight.db.SqlDriver
import ru.z8.louttsev.bustrainflightmobile.shared.infrastructure.persistence.shared.newInstance
import ru.z8.louttsev.bustrainflightmobile.shared.infrastructure.persistence.shared.schema

interface LocalDb : Transacter {
  val localDbQueries: LocalDbQueries

  companion object {
    val Schema: SqlDriver.Schema
      get() = LocalDb::class.schema

    operator fun invoke(driver: SqlDriver): LocalDb = LocalDb::class.newInstance(driver)}
}
